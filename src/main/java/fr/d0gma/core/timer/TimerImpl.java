package fr.d0gma.core.timer;

import fr.d0gma.core.Core;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

class TimerImpl implements Timer {

    private static final String HOURS_DATE_FORMAT = "%02dh %02dm %02ds";
    private static final String MINUTES_DATE_FORMAT = "%02dm %02ds";
    private static final String SECONDS_DATE_FORMAT = "%02ds";

    private final String key;
    private final Duration step;
    private long stop;

    private final Consumer<Timer> runOnTick;
    private final Consumer<Timer> runOnEnd;

    private Status status = Status.UNSTARTED;
    private long value = 0L;

    private ScheduledExecutorService executorService;

    public TimerImpl(String key, Duration step, Duration stop, Consumer<Timer> runOnTick, Consumer<Timer> runOnEnd) {
        if (step == null || step.isZero() || step.isNegative()) {
            throw new IllegalArgumentException("step must be strictly positive.");
        }

        this.key = key;
        this.step = step;
        this.stop = stop != null ? stop.dividedBy(step) : Long.MAX_VALUE;

        this.runOnTick = runOnTick;
        this.runOnEnd = runOnEnd;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void start() {

        if (this.status == Status.PAUSED) {
            resume();
            return;
        }

        if (this.status != Status.UNSTARTED) {
            return;
        }

        this.executorService = newSingleThreadScheduledExecutor(r -> Thread.ofVirtual().unstarted(r));

        Runnable task = () -> {

            if (this.status == Status.PAUSED) {
                return;
            }

            if (this.status == Status.RUNNING && this.value >= this.stop) {
                this.status = Status.ENDED;
            }

            if (this.status != Status.RUNNING) {
                executorService.shutdownNow();

                if (this.status == Status.ENDED && this.runOnEnd != null) {
                    try {
                        this.runOnEnd.accept(this);
                    } catch (Exception e) {
                        Core.getPlugin().getLogger().severe("Error while running timer end task : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                return;
            }

            value++;

            if (this.runOnTick != null) {
                try {
                    runOnTick.accept(this);
                } catch (Exception e) {
                    Core.getPlugin().getLogger().severe("Error while running timer: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        this.status = Status.RUNNING;
        executorService.scheduleAtFixedRate(task, 0L, this.step.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void pause() {
        if (this.status == Status.RUNNING) {
            this.status = Status.PAUSED;
        }
    }

    @Override
    public void resume() {
        if (this.status == Status.PAUSED) {
            this.status = Status.RUNNING;
        }
    }

    @Override
    public void cancel() {
        if (this.status == Status.RUNNING || this.status == Status.PAUSED) {
            this.status = Status.CANCELED;
            executorService.shutdownNow();
        }
    }

    @Override
    public void forceEnd() {
        if (this.status == Status.RUNNING || this.status == Status.PAUSED) {
            this.status = Status.ENDED;
            executorService.shutdownNow();
            if (this.runOnEnd != null) {
                this.runOnEnd.accept(this);
            }
        }
    }

    @Override
    public String getIncreasingFormattedValue() {
        return formatDuration(getCurrentDuration());
    }

    @Override
    public String getDecreasingFormattedValue() {
        if (this.stop == Long.MAX_VALUE) {
            return "∞";
        }

        Duration duration = this.step.multipliedBy(this.stop - this.value);
        return formatDuration(duration);
    }

    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        if (hours > 0) {
            return String.format(HOURS_DATE_FORMAT, hours, minutes, seconds);
        }
        if (minutes > 0) {
            return String.format(MINUTES_DATE_FORMAT, minutes, seconds);
        }

        return String.format(SECONDS_DATE_FORMAT, seconds);
    }

    @Override
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public long getMaxValue() {
        return this.stop;
    }

    @Override
    public long getCurrentValue() {
        return this.value;
    }

    @Override
    public void setMaxValue(long stop) {
        this.stop = stop;
    }

    @Override
    public Duration getMaxDuration() {
        return this.stop == Long.MAX_VALUE ? Duration.ZERO : this.step.multipliedBy(this.stop);
    }

    @Override
    public Duration getCurrentDuration() {
        return this.step.multipliedBy(this.value);
    }

    @Override
    public void reset() {
        this.cancel();
        this.value = 0L;
        this.status = Status.UNSTARTED;
    }
}

package fr.d0gma.core.timer;

import fr.d0gma.core.value.ITimeValue;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public class TimerService {

    private static final Map<String, Timer> TIMERS = new HashMap<>();

    public static Timer createTimer(String key, Duration step, Duration stop, Consumer<Timer> runOnTick, Consumer<Timer> runOnEnd) {
        return new TimerImpl(key, step, stop, runOnTick, runOnEnd);
    }

    public static Timer registerTimer(String key, Duration step, Duration stop, Consumer<Timer> runOnTick, Consumer<Timer> runOnEnd) {
        Timer timer = createTimer(key, step, stop, runOnTick, runOnEnd);
        registerTimer(timer);
        return timer;
    }

    public static Timer registerRunnableTimer(String key, Duration step, Duration stop, Runnable runOnTick, Runnable runOnEnd) {
        Consumer<Timer> timerRunOnTick = runOnTick != null ? timer -> runOnTick.run() : null;
        Consumer<Timer> timerRunOnEnd = runOnEnd != null ? timer -> runOnEnd.run() : null;

        return registerTimer(key, step, stop, timerRunOnTick, timerRunOnEnd);
    }

    public static Timer registerValueTimer(String key, long step, Long stop, TimeUnit timeUnit, LongConsumer runOnTick, LongConsumer runOnEnd) {
        Consumer<Timer> timerRunOnTick = runOnTick != null ? timer -> runOnTick.accept(timer.getValue()) : null;
        Consumer<Timer> timerRunOnEnd = runOnEnd != null ? timer -> runOnEnd.accept(timer.getValue()) : null;

        return registerTimer(key, Duration.of(step, timeUnit.toChronoUnit()), stop != null ? Duration.of(stop, timeUnit.toChronoUnit()) : null, timerRunOnTick, timerRunOnEnd);
    }

    public static Timer registerTimerFromTimeValue(String key, ITimeValue step, ITimeValue stop, Consumer<Timer> runOnTick, Consumer<Timer> runOnEnd) {
        Duration stopDuration = stop != null ? stop.toDuration() : null;
        return registerTimer(key, step.toDuration(), stopDuration, runOnTick, runOnEnd);
    }

    public static Optional<Timer> getTimer(String key) {
        return Optional.ofNullable(TIMERS.get(key));
    }

    public static void registerTimer(Timer timer) {
        TIMERS.put(timer.getKey(), timer);
    }

    public static boolean unregisterTimer(String key) {
        return TIMERS.remove(key) != null;
    }

    public static boolean unregisterTimer(Timer timer) {
        return unregisterTimer(timer.getKey());
    }
}

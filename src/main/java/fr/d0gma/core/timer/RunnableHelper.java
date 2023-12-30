package fr.d0gma.core.timer;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class RunnableHelper {

    private static final IntPredicate FALSE_INT_PREDICATE = i -> false;

    private static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin plugin) {
        RunnableHelper.plugin = plugin;
    }


    public static void runSynchronously(Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    public static void runAsynchronously(Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public static void runLaterSynchronously(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public static void runLaterAsynchronously(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public static BukkitTask runTimerSynchronously(Runnable runnable, long delay, long interval) {
        return plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, interval);
    }

    public static BukkitTask runTimerAsynchronously(Runnable runnable, long delay, long interval) {
        return plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, interval);
    }

    public static void runTimerSynchronously(Consumer<BukkitTask> task, long delay, long interval) {
        plugin.getServer().getScheduler().runTaskTimer(plugin, task, delay, interval);
    }

    public static void runTimerAsynchronously(Consumer<BukkitTask> task, long delay, long interval) {
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, task, delay, interval);
    }

    public static void runRepeatSynchronously(IntConsumer runnable, IntPredicate earlyCancel, long delay, long interval, int occurrences) {
        if (occurrences <= 0) {
            throw new IllegalArgumentException("the number of occurrences must be strictly greater than 0 (" + occurrences + ").");
        }

        AtomicInteger occurrencesCounter = new AtomicInteger();

        runTimerSynchronously(bukkitTask -> {

            int currentOccurrence = occurrencesCounter.getAndIncrement();

            if (currentOccurrence + 1 == occurrences || earlyCancel.test(currentOccurrence)) {
                bukkitTask.cancel();
            }

            runnable.accept(currentOccurrence);

        }, delay, interval);
    }

    public static void runRepeatAsynchronously(IntConsumer runnable, IntPredicate earlyCancel, long delay, long interval, int occurrences) {
        if (occurrences <= 0) {
            throw new IllegalArgumentException("the number of occurrences must be strictly greater than 0 (" + occurrences + ").");
        }

        AtomicInteger occurrencesCounter = new AtomicInteger();

        runTimerAsynchronously(bukkitTask -> {

            int currentOccurrence = occurrencesCounter.getAndIncrement();

            if (currentOccurrence + 1 == occurrences || earlyCancel.test(currentOccurrence)) {
                bukkitTask.cancel();
            }

            runnable.accept(currentOccurrence);

        }, delay, interval);
    }

    public static void runRepeatSynchronously(IntConsumer runnable, long delay, long interval, int occurrences) {
        runRepeatSynchronously(runnable, FALSE_INT_PREDICATE, delay, interval, occurrences);
    }

    public static void runRepeatAsynchronously(IntConsumer runnable, long delay, long interval, int occurrences) {
        runRepeatAsynchronously(runnable, FALSE_INT_PREDICATE, delay, interval, occurrences);
    }
}

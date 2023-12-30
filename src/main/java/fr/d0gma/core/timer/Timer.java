package fr.d0gma.core.timer;

import reactor.core.publisher.Flux;

/**
 * Represents a timer.
 *
 * @see TimerService
 */
public interface Timer {

    /**
     * Gets the key of the timer.
     *
     * @return the key
     */
    String getKey();

    /**
     * Starts the timer.
     */
    void start();

    /**
     * Pauses the timer.
     */
    void stop();

    /**
     * Returns the current value of the timer in an increasing format.
     *
     * @return The formatted value of the timer in an increasing format.
     */
    String getIncreasingFormattedValue();

    /**
     * Returns the current value of the timer in a decreasing format,
     * meaning {@link #getMaxValue() max} - {@link #getValue() current}.
     * If the timer has no maximum, the infinity symbol (U+221E) is returned.
     *
     * @return The formatted value of the timer in a decreasing format.
     */
    String getDecreasingFormattedValue();

    /**
     * Returns a Flux that emits this timer on each tick.
     *
     * @return A Flux that emits this timer on each tick.
     */
    Flux<Timer> onTick();

    /**
     * Adds a task to be executed when the timer ends.
     * End tasks are not called if the timer is {@link #stop() stopped} manually.
     *
     * @param consumer The task to be executed when the timer ends.
     */
    void addEndTask(Runnable consumer);

    /**
     * Sets the value of the timer to the specified value.
     *
     * @param value The new value of the timer.
     */
    void setValue(long value);

    /**
     * Returns the current value of the timer. The value is in arbitrary unit,
     * more precisely it equals the number of time that the timer loop has been run.
     *
     * @return The current value of the timer
     */
    long getValue();

    /**
     * Returns the maximum value of the timer, or {@link Long#MAX_VALUE} if
     * the timer has no maximum value.
     *
     * @return The maximum value of the timer
     */
    long getMaxValue();

    /**
     * Returns true if the timer has ended.
     *
     * @return True if the timer has ended, false otherwise
     */
    boolean isEnded();

    /**
     * Sets the maximum value of the timer to the specified value.
     *
     * @param value The new maximum value, in the corresponding arbitrary unit
     */
    void setMaxValue(long value);

    /**
     * Resets the timer to the beginning. Sets {@link #isEnded()} to false.
     */
    void reset();
}

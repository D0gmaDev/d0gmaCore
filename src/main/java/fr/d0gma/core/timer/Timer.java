package fr.d0gma.core.timer;

import java.time.Duration;

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
     * Gets the current status of the timer.
     *
     * @return the status
     */
    Status getStatus();

    /**
     * Starts the timer.
     */
    void start();

    /**
     * Pauses the timer.
     */
    void pause();

    /**
     * Resumes the timer if it is currently paused.
     */
    void resume();

    /**
     * Cancels the timer.
     */
    void cancel();

    /**
     * Ends the timer.
     */
    void forceEnd();

    /**
     * Returns the current value of the timer in an increasing format.
     *
     * @return The formatted value of the timer in an increasing format.
     */
    String getIncreasingFormattedValue();

    /**
     * Returns the current value of the timer in a decreasing format,
     * meaning {@link #getMaxValue() max} - {@link #getCurrentValue() current}.
     * If the timer has no maximum, the infinity symbol (U+221E) is returned.
     *
     * @return The formatted value of the timer in a decreasing format.
     */
    String getDecreasingFormattedValue();

    /**
     * Sets the value of the timer to the specified value.
     *
     * @param value The new value of the timer.
     */
    void setValue(long value);

    /**
     * Returns the maximum value of the timer, or {@link Long#MAX_VALUE} if
     * the timer has no maximum value.
     *
     * @return The maximum value of the timer
     */
    long getMaxValue();

    /**
     * Returns the current value of the timer.
     *
     * @return the current value of the timer
     */
    long getCurrentValue();

    /**
     * Sets the maximum value of the timer to the specified value.
     *
     * @param value The new maximum value, in the corresponding arbitrary unit
     */
    void setMaxValue(long value);

    /**
     * Returns the maximum duration of the timer.
     *
     * @return the maximum duration of the timer
     */
    Duration getMaxDuration();

    /**
     * Returns the current duration of the timer.
     *
     * @return the current duration of the timer
     */
    Duration getCurrentDuration();

    /**
     * Resets the timer to the beginning.
     * Sets the status to {@link Status#UNSTARTED}.
     */
    void reset();

    enum Status {

        UNSTARTED,
        RUNNING,
        PAUSED,
        CANCELED,
        ENDED
    }
}

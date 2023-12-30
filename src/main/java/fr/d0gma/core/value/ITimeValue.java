package fr.d0gma.core.value;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public non-sealed interface ITimeValue extends INumericValue<Long> {

    long getLongValue();

    String getFormattedTime();

    String getFormattedSmallStep();

    String getFormattedMediumStep();

    String getFormattedLargeStep();

    TimeUnit getTimeUnit();

    Duration toDuration();

}

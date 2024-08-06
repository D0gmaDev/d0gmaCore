package fr.d0gma.core.value;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public sealed interface TimeValue extends NumericValue<Long> permits TimeValueImpl {

    long getLongValue();

    String getFormattedTime();

    String getFormattedSmallStep();

    String getFormattedMediumStep();

    String getFormattedLargeStep();

    TimeUnit getTimeUnit();

    Duration toDuration();

}

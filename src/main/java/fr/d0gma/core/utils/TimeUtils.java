package fr.d0gma.core.utils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtils {

    private static final Pattern DURATION_PATTERN = Pattern.compile("^([0-9]+)(ms|s|m|h|d|j|w|mo|y)$", Pattern.CASE_INSENSITIVE);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("EEE dd MMM yyyy HH:mm:ss", Locale.FRENCH).withZone(ZoneId.of("CET"));

    /**
     * Creates a {@link TemporalAmount} from an input string, ranging from milliseconds to years.
     * The specified amount must be a positive integer and can be 0. The Optional is empty if the input cannot be parsed.
     * <p>
     * Example inputs: "15d", "6y", "521s"
     *
     * @param input the temporal amount as a String
     * @return the relevant {@link TemporalAmount} optional, meaning a {@link Duration} (hours or less) or a {@link Period}
     */
    public static Optional<TemporalAmount> parsePositiveTemporalAmount(String input) {
        Matcher matcher = DURATION_PATTERN.matcher(input);

        if (!matcher.matches()) {
            return Optional.empty();
        }

        int amount = Integer.parseInt(matcher.group(1));

        return Optional.of(switch (matcher.group(2).toLowerCase()) {
            case "ms" -> Duration.ofMillis(amount);
            case "s" -> Duration.ofSeconds(amount);
            case "m" -> Duration.ofMinutes(amount);
            case "h" -> Duration.ofHours(amount);
            case "d", "j" -> Period.ofDays(amount);
            case "w" -> Period.ofWeeks(amount);
            case "mo" -> Period.ofMonths(amount);
            case "y" -> Period.ofYears(amount);
            default -> throw new IllegalStateException();
        });
    }

    public static Duration max(Duration duration1, Duration duration2) {
        return duration1.compareTo(duration2) > 0 ? duration1 : duration2;
    }

    public static Duration min(Duration duration1, Duration duration2) {
        return duration1.compareTo(duration2) > 0 ? duration2 : duration1;
    }

    public static boolean isFirstLessOrEqual(Duration duration1, Duration duration2) {
        return duration1.compareTo(duration2) <= 0;
    }

    public static String formatDateToCET(TemporalAccessor date, Locale locale) {
        return DATE_TIME_FORMATTER.withLocale(locale).format(date);
    }

    public static String formatDateToCET(TemporalAccessor date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    public static String format(TemporalAmount temporalAmount) {
        return switch (temporalAmount) {
            case Duration duration -> formatDuration(duration);
            case Period period -> formatPeriod(period);
            default -> throw new IllegalStateException("Unexpected value: " + temporalAmount);
        };
    }

    private static String formatDuration(Duration duration) {
        if (duration.isZero()) {
            return "∅";
        }

        List<String> parts = new ArrayList<>(5);
        appendIfNonZero(parts, (int) duration.toDaysPart(), "j");
        appendIfNonZero(parts, duration.toHoursPart(), "h");
        appendIfNonZero(parts, duration.toMinutesPart(), "m");
        appendIfNonZero(parts, duration.toSecondsPart(), "s");
        appendIfNonZero(parts, duration.toMillisPart(), "ms");

        return String.join(" ", parts);
    }

    private static String formatPeriod(Period period) {
        if (period.isZero()) {
            return "∅";
        }

        List<String> parts = new ArrayList<>(3);
        appendIfNonZero(parts, period.getYears(), "y");
        appendIfNonZero(parts, period.getMonths(), "mo");
        appendIfNonZero(parts, period.getDays(), "j");

        return String.join(" ", parts);
    }

    private static void appendIfNonZero(List<String> list, int value, String unit) {
        if (value > 0) {
            list.add(value + unit);
        }
    }

    public static boolean isBetween(OffsetDateTime dateTime, OffsetDateTime beginning, OffsetDateTime end) {
        return dateTime.isAfter(beginning) && dateTime.isBefore(end);
    }

}

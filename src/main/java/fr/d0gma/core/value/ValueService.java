package fr.d0gma.core.value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ValueService {

    private static final UnsafeValueMap<NumericValue<? extends Number>> NUMERIC_MAP = new UnsafeValueMap<>();
    private static final UnsafeValueMap<BooleanValue> BOOLEAN_MAP = new UnsafeValueMap<>();
    private static final UnsafeValueMap<TimeValue> TIME_MAP = new UnsafeValueMap<>();
    private static final UnsafeValueMap<EnumValue<?>> ENUM_MAP = new UnsafeValueMap<>();
    private static final UnsafeValueMap<StringValue> STRING_MAP = new UnsafeValueMap<>();


    public static <T extends Number & Comparable<T>> NumericValue<T> registerNumericValue(String key, String nameKey, String descriptionKey, T defaultValue, T minValue, T maxValue, T smallStep, T mediumStem, T largeStep) {
        return NUMERIC_MAP.registerUnsafeValue(key, createNumericValue(nameKey, descriptionKey, defaultValue, minValue, maxValue, smallStep, mediumStem, largeStep));
    }


    public static BooleanValue registerBooleanValue(String key, String nameKey, String trueDescriptionKey, String falseDescriptionKey, boolean defaultValue) {
        return BOOLEAN_MAP.registerValue(key, createBooleanValue(nameKey, trueDescriptionKey, falseDescriptionKey, defaultValue));
    }


    public static TimeValue registerTimeValue(String key, String nameKey, String descriptionKey, long defaultValue, long minValue, long maxValue, long smallStep, long mediumStem, long largeStep, TimeUnit timeUnit) {
        return TIME_MAP.registerValue(key, createTimeValue(nameKey, descriptionKey, defaultValue, minValue, maxValue, smallStep, mediumStem, largeStep, timeUnit));
    }

    public static <T extends Enum<T>> EnumValue<T> registerEnumValue(String key, String nameKey, Class<T> enumerationClass, T defaultValue, String... descriptionKeys) {
        return ENUM_MAP.registerUnsafeValue(key, createEnumValue(nameKey, enumerationClass, defaultValue, descriptionKeys));
    }

    public static StringValue registerStringValue(String key, String nameKey, String descriptionKey, String defaultValue, int minLength, int maxLength) {
        return STRING_MAP.registerValue(key, createStringValue(nameKey, descriptionKey, defaultValue, minLength, maxLength));
    }


    public static <T extends Number & Comparable<T>> NumericValue<T> createNumericValue(String nameKey, String descriptionKey, T defaultValue, T minValue, T maxValue, T smallStep, T mediumStem, T largeStep) {
        return new NumericValueImpl<>(nameKey, descriptionKey, defaultValue, minValue, maxValue, smallStep, mediumStem, largeStep);
    }


    public static BooleanValue createBooleanValue(String nameKey, String trueDescriptionKey, String falseDescriptionKey, boolean defaultValue) {
        return new BooleanValueImpl(nameKey, trueDescriptionKey, falseDescriptionKey, defaultValue);
    }


    public static TimeValue createTimeValue(String nameKey, String descriptionKey, long defaultValue, long minValue, long maxValue, long smallStep, long mediumStem, long largeStep, TimeUnit timeUnit) {
        return new TimeValueImpl(nameKey, descriptionKey, defaultValue, minValue, maxValue, smallStep, mediumStem, largeStep, timeUnit);
    }


    public static <T extends Enum<T>> EnumValue<T> createEnumValue(String nameKey, Class<T> enumerationClass, T defaultValue, String... descriptionKeys) {
        return new EnumValueImpl<>(nameKey, enumerationClass, defaultValue, descriptionKeys);
    }

    public static StringValue createStringValue(String nameKey, String descriptionKey, String defaultValue, int minLength, int maxLength) {
        return new StringValueImpl(nameKey, descriptionKey, defaultValue, minLength, maxLength);
    }

    public static <T extends Number & Comparable<T>> Optional<NumericValue<T>> getNumericValue(Class<T> numericType, String key) {
        return Optional.ofNullable(NUMERIC_MAP.getUnsafeValue(key));
    }

    public static Optional<BooleanValue> getBooleanValue(String key) {
        return Optional.ofNullable(BOOLEAN_MAP.getValue(key));
    }


    public static Optional<TimeValue> getTimeValue(String key) {
        return Optional.ofNullable(TIME_MAP.getValue(key));
    }

    public static <T extends Enum<T>> Optional<EnumValue<T>> getEnumValue(Class<T> enumClass, String key) {
        return Optional.ofNullable(ENUM_MAP.getUnsafeValue(key));
    }

    public static Optional<StringValue> getStringValue(String key) {
        return Optional.ofNullable(STRING_MAP.getValue(key));
    }

    public static void removeValue(String key) {
        NUMERIC_MAP.remove(key);
        BOOLEAN_MAP.remove(key);
        TIME_MAP.remove(key);
        ENUM_MAP.remove(key);
        STRING_MAP.remove(key);
    }

    private static class UnsafeValueMap<T> {

        private final Map<String, T> values = new HashMap<>();

        public T registerValue(String key, T value) {
            values.put(key, value);
            return value;
        }

        @SuppressWarnings("unchecked")
        public <U extends T> U registerUnsafeValue(String key, T value) {
            return (U) registerValue(key, value);
        }

        public T getValue(String key) {
            return values.get(key);
        }

        @SuppressWarnings("unchecked")
        public <U extends T> U getUnsafeValue(String key) {
            return (U) getValue(key);
        }

        public void remove(String key) {
            values.remove(key);
        }
    }
}

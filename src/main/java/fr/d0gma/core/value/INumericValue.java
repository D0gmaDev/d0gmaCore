package fr.d0gma.core.value;

public sealed interface INumericValue<T extends Number> extends GeneralValue<T> permits ITimeValue, NumericValue {

    String getDescriptionKey();

    String getStringValue();

    T getMaxValue();

    T getMinValue();

    T getSmallStep();

    T getMediumStep();

    T getLargeStep();

    void smallIncrement();

    void mediumIncrement();

    void largeIncrement();

    void smallDecrement();

    void mediumDecrement();

    void largeDecrement();
}

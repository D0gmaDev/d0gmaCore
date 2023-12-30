package fr.d0gma.core.value;

public sealed interface GeneralValue<T> extends Restrictible<T> permits IBooleanValue, IEnumValue, INumericValue, IStringValue, Value {

    String getNameKey();

    T getValue();

    void setValue(T value);

    boolean isEdited();

    T getDefaultValue();

    void reset();

    IOnValueChanged<T> subscribeOnValueChanged(IOnValueChanged<T> onChanged);

    boolean unsubscribeOnValueChanged(IOnValueChanged<T> onChanged);

}

package fr.d0gma.core.value;

public sealed interface GeneralValue<T> extends Restrictible<T> permits BooleanValue, EnumValue, NumericValue, StringValue {

    String getNameKey();

    T getValue();

    void setValue(T value);

    boolean isEdited();

    T getDefaultValue();

    void reset();

    OnValueChanged<T> subscribeOnValueChanged(OnValueChanged<T> onChanged);

    boolean unsubscribeOnValueChanged(OnValueChanged<T> onChanged);

}

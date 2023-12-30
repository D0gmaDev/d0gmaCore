package fr.d0gma.core.value;

public sealed interface IBooleanValue extends GeneralValue<Boolean> permits BooleanValue {

    String getDescriptionKey();

    boolean getBooleanValue();

    void toggle();
}

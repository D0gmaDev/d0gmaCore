package fr.d0gma.core.value;

public sealed interface BooleanValue extends GeneralValue<Boolean> permits BooleanValueImpl {

    String getDescriptionKey();

    boolean getBooleanValue();

    void toggle();
}

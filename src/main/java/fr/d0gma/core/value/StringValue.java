package fr.d0gma.core.value;

public sealed interface StringValue extends GeneralValue<String> permits StringValueImpl {

    String getDescriptionKey();

    int getMinLength();

    int getMaxLength();
}

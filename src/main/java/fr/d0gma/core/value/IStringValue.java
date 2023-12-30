package fr.d0gma.core.value;

public sealed interface IStringValue extends GeneralValue<String> permits StringValue {

    String getDescriptionKey();

    int getMinLength();

    int getMaxLength();
}

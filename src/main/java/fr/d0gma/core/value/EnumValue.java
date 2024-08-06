package fr.d0gma.core.value;

public sealed interface EnumValue<T extends Enum<T>> extends GeneralValue<T> permits EnumValueImpl {

    void increment();

    void decrement();

    String getSelectedDescriptionKey();

    int getValueIndex();

    String[] getDescriptionKeys();

    T[] getEnumeration();

    void setIndexValue(int index);

    int getDefaultValueIndex();
}

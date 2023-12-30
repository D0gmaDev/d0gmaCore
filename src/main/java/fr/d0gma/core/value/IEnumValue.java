package fr.d0gma.core.value;

public sealed interface IEnumValue<T extends Enum<T>> extends GeneralValue<T> permits EnumValue {

    void increment();

    void decrement();

    String getSelectedDescriptionKey();

    int getValueIndex();

    String[] getDescriptionKeys();

    T[] getEnumeration();

    void setIndexValue(int index);

    int getDefaultValueIndex();
}

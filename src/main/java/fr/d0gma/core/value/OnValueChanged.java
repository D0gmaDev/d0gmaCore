package fr.d0gma.core.value;

@FunctionalInterface
public interface OnValueChanged<T> {

    void valueChanged(T oldValue, T newValue);
}

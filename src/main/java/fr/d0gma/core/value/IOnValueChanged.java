package fr.d0gma.core.value;

@FunctionalInterface
public interface IOnValueChanged<T> {

    void valueChanged(T oldValue, T newValue);
}

package fr.d0gma.core.value;

public interface Restrictible<T> {

    ValueRestriction<T> addRestriction(RestrictionType type, T value);

    void addRestriction(ValueRestriction<T> restriction);

    void removeRestriction(ValueRestriction<T> restriction);

    boolean canApply(ValueRestriction<T> restriction);

    boolean isLocked();

}

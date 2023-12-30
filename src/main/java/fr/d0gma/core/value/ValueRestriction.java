package fr.d0gma.core.value;

import java.util.Objects;
import java.util.UUID;

public record ValueRestriction<T>(UUID id, RestrictionType type, T value) {

    public ValueRestriction {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        Objects.requireNonNull(value);

        if ((type == RestrictionType.MAXIMAL_VALUE || type == RestrictionType.MINIMAL_VALUE) && !(value instanceof Comparable<?>)) {
            throw new IllegalArgumentException(type + " restriction must be applied on a Comparable");
        }
    }

    public boolean isType(RestrictionType type) {
        return type() == type;
    }
}

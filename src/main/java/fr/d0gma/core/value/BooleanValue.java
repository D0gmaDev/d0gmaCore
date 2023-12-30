package fr.d0gma.core.value;

import java.util.Objects;

public final class BooleanValue extends Value<Boolean> implements IBooleanValue {

    private final String nameKey;
    private final String trueDescriptionKey;
    private final String falseDescriptionKey;
    private final boolean defaultValue;

    private boolean value;

    public BooleanValue(String nameKey, String trueDescriptionKey, String falseDescriptionKey, boolean defaultValue) {
        this.nameKey = Objects.requireNonNull(nameKey);
        this.trueDescriptionKey = trueDescriptionKey;
        this.falseDescriptionKey = falseDescriptionKey;

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public String getNameKey() {
        return this.nameKey;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        boolean oldValue = this.value;
        this.value = value != null && value;
        onValueChanged(oldValue, this.value);
    }

    @Override
    public boolean isEdited() {
        return getValue() != getDefaultValue();
    }

    @Override
    public Boolean getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public String getDescriptionKey() {
        return getBooleanValue() ? this.trueDescriptionKey : this.falseDescriptionKey;
    }

    @Override
    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public void toggle() {
        setValue(!getBooleanValue());
    }

    @Override
    public void reset() {
        setValue(getDefaultValue());
    }

    @Override
    public void addRestriction(ValueRestriction<Boolean> valueRestriction) {
        super.addRestriction(valueRestriction);

        if (valueRestriction.isType(RestrictionType.LOCKED_VALUE) && valueRestriction.value() != getBooleanValue()) {
            toggle();
        }
    }

    @Override
    public String toString() {
        return "IBooleanValue(" + value + ')';
    }
}

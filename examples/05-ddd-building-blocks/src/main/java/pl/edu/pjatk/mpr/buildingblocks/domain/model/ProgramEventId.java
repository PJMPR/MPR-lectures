package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.util.Objects;

public final class ProgramEventId {
    private final String value;

    public ProgramEventId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Program event id cannot be blank");
        }
        this.value = value.trim();
    }

    public String value() { return value; }

    @Override public boolean equals(Object other) {
        return this == other || other instanceof ProgramEventId && value.equals(((ProgramEventId) other).value);
    }

    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}

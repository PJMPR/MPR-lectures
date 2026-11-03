package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.util.Objects;

public final class RoomId {
    private final String value;

    public RoomId(String value) {
        if (value == null || !value.trim().matches("[A-Z][A-Z0-9-]{0,9}")) {
            throw new IllegalArgumentException("Room id must use an uppercase business code");
        }
        this.value = value.trim();
    }

    public String value() { return value; }

    @Override public boolean equals(Object other) {
        return this == other || other instanceof RoomId && value.equals(((RoomId) other).value);
    }

    @Override public int hashCode() { return Objects.hash(value); }
    @Override public String toString() { return value; }
}

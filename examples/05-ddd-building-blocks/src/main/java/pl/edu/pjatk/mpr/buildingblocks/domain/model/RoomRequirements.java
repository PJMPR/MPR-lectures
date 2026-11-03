package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.util.Objects;

public final class RoomRequirements {
    private final int minimumCapacity;
    private final boolean projectorRequired;
    private final boolean stageRequired;

    public RoomRequirements(int minimumCapacity, boolean projectorRequired, boolean stageRequired) {
        if (minimumCapacity < 1) throw new IllegalArgumentException("Minimum capacity must be positive");
        this.minimumCapacity = minimumCapacity;
        this.projectorRequired = projectorRequired;
        this.stageRequired = stageRequired;
    }

    public int minimumCapacity() { return minimumCapacity; }
    public boolean projectorRequired() { return projectorRequired; }
    public boolean stageRequired() { return stageRequired; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof RoomRequirements)) return false;
        RoomRequirements that = (RoomRequirements) other;
        return minimumCapacity == that.minimumCapacity && projectorRequired == that.projectorRequired && stageRequired == that.stageRequired;
    }

    @Override public int hashCode() { return Objects.hash(minimumCapacity, projectorRequired, stageRequired); }
}

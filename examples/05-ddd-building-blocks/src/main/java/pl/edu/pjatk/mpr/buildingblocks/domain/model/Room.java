package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.util.Objects;

public final class Room {
    private final RoomId id;
    private final int capacity;
    private final boolean projector;
    private final boolean stage;

    public Room(RoomId id, int capacity, boolean projector, boolean stage) {
        this.id = Objects.requireNonNull(id, "id");
        if (capacity < 1) throw new IllegalArgumentException("Room capacity must be positive");
        this.capacity = capacity;
        this.projector = projector;
        this.stage = stage;
    }

    public RoomId id() { return id; }
    public int capacity() { return capacity; }
    public boolean hasProjector() { return projector; }
    public boolean hasStage() { return stage; }
}

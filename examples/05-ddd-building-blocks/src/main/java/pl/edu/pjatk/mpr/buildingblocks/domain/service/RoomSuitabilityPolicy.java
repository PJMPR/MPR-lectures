package pl.edu.pjatk.mpr.buildingblocks.domain.service;

import pl.edu.pjatk.mpr.buildingblocks.domain.model.Room;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomRequirements;

import java.util.Objects;

public final class RoomSuitabilityPolicy {
    public boolean isSuitable(RoomRequirements requirements, Room room) {
        Objects.requireNonNull(requirements, "requirements");
        Objects.requireNonNull(room, "room");
        return room.capacity() >= requirements.minimumCapacity()
            && (!requirements.projectorRequired() || room.hasProjector())
            && (!requirements.stageRequired() || room.hasStage());
    }
}

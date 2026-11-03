package pl.edu.pjatk.mpr.buildingblocks.domain.event;

import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEventId;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomId;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.TimeSlot;

import java.util.Objects;

public final class ProgramEventScheduled {
    private final ProgramEventId eventId;
    private final RoomId roomId;
    private final TimeSlot timeSlot;

    public ProgramEventScheduled(ProgramEventId eventId, RoomId roomId, TimeSlot timeSlot) {
        this.eventId = Objects.requireNonNull(eventId, "eventId");
        this.roomId = Objects.requireNonNull(roomId, "roomId");
        this.timeSlot = Objects.requireNonNull(timeSlot, "timeSlot");
    }

    public ProgramEventId eventId() { return eventId; }
    public RoomId roomId() { return roomId; }
    public TimeSlot timeSlot() { return timeSlot; }
}

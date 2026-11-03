package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.util.Objects;

public final class Schedule {
    private final RoomId roomId;
    private final TimeSlot timeSlot;

    public Schedule(RoomId roomId, TimeSlot timeSlot) {
        this.roomId = Objects.requireNonNull(roomId, "roomId");
        this.timeSlot = Objects.requireNonNull(timeSlot, "timeSlot");
    }

    public RoomId roomId() { return roomId; }
    public TimeSlot timeSlot() { return timeSlot; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Schedule)) return false;
        Schedule that = (Schedule) other;
        return roomId.equals(that.roomId) && timeSlot.equals(that.timeSlot);
    }

    @Override public int hashCode() { return Objects.hash(roomId, timeSlot); }
}

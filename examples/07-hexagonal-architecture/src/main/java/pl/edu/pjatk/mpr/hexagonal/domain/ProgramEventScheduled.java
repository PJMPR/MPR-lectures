package pl.edu.pjatk.mpr.hexagonal.domain;

import java.time.LocalDate;

public final class ProgramEventScheduled {
    private final String eventId;
    private final String roomId;
    private final LocalDate date;
    private final TimeSlot timeSlot;

    public ProgramEventScheduled(String eventId, String roomId, LocalDate date, TimeSlot timeSlot) {
        this.eventId = eventId; this.roomId = roomId; this.date = date; this.timeSlot = timeSlot;
    }
    public String eventId() { return eventId; }
    public String roomId() { return roomId; }
    public LocalDate date() { return date; }
    public TimeSlot timeSlot() { return timeSlot; }
}

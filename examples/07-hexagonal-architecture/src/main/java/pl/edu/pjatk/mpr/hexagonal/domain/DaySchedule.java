package pl.edu.pjatk.mpr.hexagonal.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DaySchedule {
    private final LocalDate date;
    private final List<Entry> entries = new ArrayList<>();

    private DaySchedule(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public static DaySchedule empty(LocalDate date) {
        return new DaySchedule(date);
    }

    public ProgramEventScheduled schedule(String eventId, String roomId, TimeSlot timeSlot) {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(roomId);
        Objects.requireNonNull(timeSlot);
        boolean conflict = entries.stream().anyMatch(e -> e.roomId.equals(roomId) && e.timeSlot.overlaps(timeSlot));
        if (conflict) throw new SchedulingConflict("Room " + roomId + " is already occupied");
        entries.add(new Entry(eventId, roomId, timeSlot));
        return new ProgramEventScheduled(eventId, roomId, date, timeSlot);
    }

    public LocalDate date() {
        return date;
    }

    public int size() {
        return entries.size();
    }

    private record Entry(String eventId, String roomId, TimeSlot timeSlot) {}
}

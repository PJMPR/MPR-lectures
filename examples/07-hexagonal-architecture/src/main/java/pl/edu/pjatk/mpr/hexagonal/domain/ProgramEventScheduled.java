package pl.edu.pjatk.mpr.hexagonal.domain;

import java.time.LocalDate;
import java.util.Objects;

public record ProgramEventScheduled(String eventId, String roomId, LocalDate date, TimeSlot timeSlot) {
    public ProgramEventScheduled {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(roomId);
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeSlot);
    }
}

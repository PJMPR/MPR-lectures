package pl.edu.pjatk.mpr.hexagonal.application.ports.in;

import java.time.LocalDate;
import java.util.Objects;

import pl.edu.pjatk.mpr.hexagonal.domain.TimeSlot;

public record ScheduleProgramEventCommand(String eventId, String roomId, LocalDate date, TimeSlot timeSlot) {
    public ScheduleProgramEventCommand {
        Objects.requireNonNull(eventId);
        Objects.requireNonNull(roomId);
        Objects.requireNonNull(date);
        Objects.requireNonNull(timeSlot);
    }
}

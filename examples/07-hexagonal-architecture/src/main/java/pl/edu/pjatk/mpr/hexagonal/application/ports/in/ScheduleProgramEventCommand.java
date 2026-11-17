package pl.edu.pjatk.mpr.hexagonal.application.ports.in;

import java.time.LocalDate;
import java.util.Objects;
import pl.edu.pjatk.mpr.hexagonal.domain.TimeSlot;

public final class ScheduleProgramEventCommand {
    private final String eventId; private final String roomId; private final LocalDate date; private final TimeSlot timeSlot;
    public ScheduleProgramEventCommand(String eventId, String roomId, LocalDate date, TimeSlot timeSlot) {
        this.eventId=Objects.requireNonNull(eventId); this.roomId=Objects.requireNonNull(roomId); this.date=Objects.requireNonNull(date); this.timeSlot=Objects.requireNonNull(timeSlot);
    }
    public String eventId(){return eventId;} public String roomId(){return roomId;} public LocalDate date(){return date;} public TimeSlot timeSlot(){return timeSlot;}
}

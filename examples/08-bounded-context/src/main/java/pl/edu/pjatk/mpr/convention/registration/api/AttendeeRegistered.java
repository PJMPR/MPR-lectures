package pl.edu.pjatk.mpr.convention.registration.api;
import java.util.Objects;
public record AttendeeRegistered(String attendeeId, String displayName, String ticketType) {
    public AttendeeRegistered { Objects.requireNonNull(attendeeId); Objects.requireNonNull(displayName); Objects.requireNonNull(ticketType); }
}

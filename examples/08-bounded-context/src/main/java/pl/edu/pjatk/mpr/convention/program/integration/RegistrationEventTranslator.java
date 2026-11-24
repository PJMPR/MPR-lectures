package pl.edu.pjatk.mpr.convention.program.integration;
import pl.edu.pjatk.mpr.convention.program.domain.DisplayName;
import pl.edu.pjatk.mpr.convention.program.domain.ExternalPersonId;
import pl.edu.pjatk.mpr.convention.program.domain.SpeakerCandidate;
import pl.edu.pjatk.mpr.convention.registration.api.AttendeeRegistered;
public final class RegistrationEventTranslator {
    public SpeakerCandidate translate(AttendeeRegistered event) { return new SpeakerCandidate(new ExternalPersonId(event.attendeeId()), DisplayName.of(event.displayName())); }
}

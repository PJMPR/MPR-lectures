package pl.edu.pjatk.mpr.convention.program.integration;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.convention.registration.api.AttendeeRegistered;
import static org.junit.jupiter.api.Assertions.assertEquals;
class RegistrationEventTranslatorTest {
    private final RegistrationEventTranslator translator = new RegistrationEventTranslator();
    @Test void translates_identity_into_the_program_language() { var candidate = translator.translate(new AttendeeRegistered("p-42", " Ada Nowak ", "VIP")); assertEquals("p-42", candidate.externalPersonId().value()); assertEquals("Ada Nowak", candidate.displayName().value()); }
    @Test void ticket_type_does_not_change_the_program_model() { var standard = translator.translate(new AttendeeRegistered("p-42", "Ada Nowak", "STANDARD")); var vip = translator.translate(new AttendeeRegistered("p-42", "Ada Nowak", "VIP")); assertEquals(standard, vip); }
}

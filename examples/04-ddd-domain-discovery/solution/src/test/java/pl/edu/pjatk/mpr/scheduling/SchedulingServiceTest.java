package pl.edu.pjatk.mpr.scheduling;
import org.junit.jupiter.api.Test; import java.util.ArrayList; import java.util.Collections; import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.*;
class SchedulingServiceTest {
    private final SchedulingService service = new SchedulingService();
    @Test void eventWithoutRoom_isRejected() { assertThat(rejected(command("Panel", "host-7", null, 600, 660)), is("Program event requires a room")); }
    @Test void validProgramEvent_isScheduled() { assertThat(service.schedule(command("Panel", "host-7", "room-a", 600, 660)).event(), instanceOf(ProgramEventScheduled.class)); }
    @Test void overlappingProgramEvents_cannotUseTheSameRoom() { service.schedule(command("Panel", "host-7", "room-a", 600, 660)); assertThat(rejected(command("Turniej", "host-8", "room-a", 630, 690)), is("Room is already occupied")); }
    @Test void host_cannotRunOverlappingProgramEvents() { service.schedule(command("Panel", "host-7", "room-a", 600, 660)); assertThat(rejected(command("Prelekcja", "host-7", "room-b", 630, 690)), is("Host has another program event")); }
    @Test void command_copiesEquipment() { List<String> equipment = new ArrayList<>(); equipment.add("projector"); ScheduleProgramEventCommand command = new ScheduleProgramEventCommand("Panel", "host-7", "room-a", 600, 660, equipment); equipment.clear(); assertThat(command.requiredEquipment(), contains("projector")); }
    @Test void scheduledEvents_receiveUniqueIdentity() { ProgramEventScheduled first = scheduled(command("Panel", "host-7", "room-a", 600, 660)); ProgramEventScheduled second = scheduled(command("Panel", "host-7", "room-a", 700, 760)); assertThat(first.programEventId(), not(equalTo(second.programEventId()))); }
    private String rejected(ScheduleProgramEventCommand command) { return ((SchedulingRejected) service.schedule(command).event()).reason(); }
    private ProgramEventScheduled scheduled(ScheduleProgramEventCommand command) { return (ProgramEventScheduled) service.schedule(command).event(); }
    private ScheduleProgramEventCommand command(String title, String host, String room, int start, int end) { return new ScheduleProgramEventCommand(title, host, room, start, end, Collections.singletonList("projector")); }
}

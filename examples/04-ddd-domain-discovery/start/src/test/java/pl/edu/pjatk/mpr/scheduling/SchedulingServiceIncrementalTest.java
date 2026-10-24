package pl.edu.pjatk.mpr.scheduling;
import org.junit.jupiter.api.Disabled; import org.junit.jupiter.api.Test;
import java.util.ArrayList; import java.util.Collections; import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat; import static org.hamcrest.Matchers.*;

/** Usuń @Disabled z jednego testu, zaimplementuj zachowanie i przejdź do kolejnego kroku. */
class SchedulingServiceIncrementalTest {
    private final SchedulingService service = new SchedulingService();

    /** KROK 1. Sala jest konieczna, bo bez niej punkt nie jest częścią harmonogramu. Reguła jest opisana językiem domeny, bez REST i bazy. */
    @Test
    void eventWithoutRoom_isRejected() {
        ScheduleResult result = service.schedule(command("Panel o Marsie", "host-7", null, 600, 660));
        assertThat(result.event(), instanceOf(SchedulingRejected.class));
        assertThat(((SchedulingRejected) result.event()).reason(), is("Program event requires a room"));
    }

    /** KROK 2. ProgramEventScheduled opisuje fakt biznesowy w czasie przeszłym i pochodzi z Ubiquitous Language. */
    @Test @Disabled("KROK 2: zwróć zdarzenie ProgramEventScheduled")
    void validProgramEvent_isScheduled() {
        ScheduleResult result = service.schedule(command("Panel o Marsie", "host-7", "room-a", 600, 660));
        assertThat(result.event(), instanceOf(ProgramEventScheduled.class));
        ProgramEventScheduled event = (ProgramEventScheduled) result.event();
        assertThat(event.title(), is("Panel o Marsie")); assertThat(event.roomId(), is("room-a"));
    }

    /** KROK 3. Dwa nakładające się punkty nie mogą zajmować tej samej sali — to reguła harmonogramu, nie ograniczenie SQL. */
    @Test @Disabled("KROK 3: wykryj konflikt sali")
    void overlappingProgramEvents_cannotUseTheSameRoom() {
        service.schedule(command("Panel o Marsie", "host-7", "room-a", 600, 660));
        ScheduleResult result = service.schedule(command("Cyberpunk 2077", "host-8", "room-a", 630, 690));
        assertThat(((SchedulingRejected) result.event()).reason(), is("Room is already occupied"));
    }

    /** KROK 4. Prowadzący nie może być w dwóch miejscach jednocześnie; konflikt istnieje nawet w różnych salach. */
    @Test @Disabled("KROK 4: wykryj konflikt prowadzącego")
    void host_cannotRunOverlappingProgramEvents() {
        service.schedule(command("Panel o Marsie", "host-7", "room-a", 600, 660));
        ScheduleResult result = service.schedule(command("Kontakt z obcymi", "host-7", "room-b", 630, 690));
        assertThat(((SchedulingRejected) result.event()).reason(), is("Host has another program event"));
    }

    /** KROK 5. Komenda wyraża jedną stabilną intencję; późniejsza zmiana listy wejściowej nie może zmienić jej znaczenia. */
    @Test @Disabled("KROK 5: wykonaj defensywną kopię wymagań technicznych")
    void command_doesNotChangeWhenEquipmentListIsModified() {
        List<String> equipment = new ArrayList<>(); equipment.add("projector");
        ScheduleProgramEventCommand command = new ScheduleProgramEventCommand("Panel", "host-7", "room-a", 600, 660, equipment);
        equipment.clear();
        assertThat(command.requiredEquipment(), contains("projector"));
    }

    /** KROK 6. Dwa punkty o takich samych danych pozostają osobnymi bytami i otrzymują różne tożsamości. */
    @Test @Disabled("KROK 6: nadaj unikalną tożsamość punktowi programu")
    void eachScheduledProgramEvent_receivesAUniqueIdentity() {
        ProgramEventScheduled first = (ProgramEventScheduled) service.schedule(command("Panel", "host-7", "room-a", 600, 660)).event();
        ProgramEventScheduled second = (ProgramEventScheduled) service.schedule(command("Panel", "host-7", "room-a", 700, 760)).event();
        assertThat(first.programEventId(), not(is(emptyOrNullString()))); assertThat(second.programEventId(), not(equalTo(first.programEventId())));
    }

    private ScheduleProgramEventCommand command(String title, String host, String room, int start, int end) {
        return new ScheduleProgramEventCommand(title, host, room, start, end, Collections.singletonList("projector"));
    }
}

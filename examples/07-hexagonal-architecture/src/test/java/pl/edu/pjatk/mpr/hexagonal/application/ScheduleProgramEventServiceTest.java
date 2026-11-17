package pl.edu.pjatk.mpr.hexagonal.application;

import static org.junit.jupiter.api.Assertions.*;
import java.time.*;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.hexagonal.adapters.out.*;
import pl.edu.pjatk.mpr.hexagonal.application.ports.in.ScheduleProgramEventCommand;
import pl.edu.pjatk.mpr.hexagonal.domain.TimeSlot;

class ScheduleProgramEventServiceTest {
    @Test void saves_schedule_and_notifies_participants(){
        var repository=new InMemoryDayScheduleRepository(); var notifier=new CollectingParticipantNotifier();
        var service=new ScheduleProgramEventService(repository,notifier);
        var command=new ScheduleProgramEventCommand("panel-42","hall-a",LocalDate.of(2026,11,14),new TimeSlot(LocalTime.of(10,0),LocalTime.of(11,0)));
        var result=service.handle(command);
        assertTrue(result.scheduled()); assertEquals(1,repository.savedSchedules()); assertEquals("panel-42",notifier.onlyEvent().eventId());
    }
}

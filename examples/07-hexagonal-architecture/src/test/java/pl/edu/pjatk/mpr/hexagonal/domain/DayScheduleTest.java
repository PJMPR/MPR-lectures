package pl.edu.pjatk.mpr.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.time.*;
import org.junit.jupiter.api.Test;

class DayScheduleTest {
    @Test void rejects_overlapping_events_in_the_same_room(){
        var schedule=DaySchedule.empty(LocalDate.of(2026,11,14));
        schedule.schedule("panel","hall-a",new TimeSlot(LocalTime.of(10,0),LocalTime.of(11,0)));
        assertThrows(SchedulingConflict.class,()->schedule.schedule("workshop","hall-a",new TimeSlot(LocalTime.of(10,30),LocalTime.of(11,30))));
    }
    @Test void permits_the_same_time_in_different_rooms(){
        var schedule=DaySchedule.empty(LocalDate.of(2026,11,14));
        var slot=new TimeSlot(LocalTime.of(10,0),LocalTime.of(11,0));
        schedule.schedule("panel","hall-a",slot); schedule.schedule("workshop","hall-b",slot);
        assertEquals(2,schedule.size());
    }
}

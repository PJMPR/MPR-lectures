package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeSlotTest {
    @Test
    void rejectsSlotsShorterThanFifteenMinutes() {
        LocalDateTime start = LocalDateTime.of(2026, 11, 14, 10, 0);

        assertThrows(IllegalArgumentException.class,
            () -> new TimeSlot(start, start.plusMinutes(14)));
    }

    @Test
    void recognizesOverlappingSlots() {
        TimeSlot first = slot(10, 0, 11, 0);
        TimeSlot overlapping = slot(10, 30, 11, 30);
        TimeSlot adjacent = slot(11, 0, 12, 0);

        assertTrue(first.overlaps(overlapping));
        assertFalse(first.overlaps(adjacent));
    }

    private TimeSlot slot(int startHour, int startMinute, int endHour, int endMinute) {
        return new TimeSlot(
            LocalDateTime.of(2026, 11, 14, startHour, startMinute),
            LocalDateTime.of(2026, 11, 14, endHour, endMinute));
    }
}

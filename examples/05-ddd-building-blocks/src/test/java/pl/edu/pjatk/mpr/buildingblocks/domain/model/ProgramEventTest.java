package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.buildingblocks.domain.exception.DomainRuleViolation;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProgramEventTest {
    @Test
    void schedulesConfirmedEventInRoomAndTimeSlot() {
        ProgramEvent event = proposedEvent();
        event.confirmSpeaker();
        TimeSlot slot = slot(10, 0, 11, 0);

        event.scheduleIn(new RoomId("A-17"), slot);

        assertThat(event.status(), is(ProgramEventStatus.SCHEDULED));
        assertThat(event.schedule().orElseThrow(AssertionError::new), is(new Schedule(new RoomId("A-17"), slot)));
    }

    @Test
    void rejectsSchedulingWithoutSpeakerConfirmation() {
        ProgramEvent event = proposedEvent();

        assertThrows(DomainRuleViolation.class,
            () -> event.scheduleIn(new RoomId("A-17"), slot(10, 0, 11, 0)));
    }

    @Test
    void reschedulingPreservesIdentity() {
        ProgramEvent event = proposedEvent();
        ProgramEventId originalId = event.id();
        event.confirmSpeaker();
        event.scheduleIn(new RoomId("A-17"), slot(10, 0, 11, 0));

        event.reschedule(new RoomId("B-04"), slot(12, 0, 13, 0));

        assertThat(event.id(), is(originalId));
        assertThat(event.schedule().orElseThrow(AssertionError::new).roomId(), is(new RoomId("B-04")));
    }

    @Test
    void withdrawnEventKeepsIdentityButReleasesSchedule() {
        ProgramEvent event = proposedEvent();
        ProgramEventId originalId = event.id();
        event.confirmSpeaker();
        event.scheduleIn(new RoomId("A-17"), slot(10, 0, 11, 0));

        event.withdraw();

        assertThat(event.id(), is(originalId));
        assertThat(event.status(), is(ProgramEventStatus.WITHDRAWN));
        assertThat(event.schedule().isPresent(), is(false));
    }

    @Test
    void entitiesWithSameIdAreEqualDespiteDifferentTitles() {
        ProgramEvent first = ProgramEvent.proposed(
            new ProgramEventId("evt-42"), new EventTitle("AI i świat"), requirements());
        ProgramEvent renamedCopy = ProgramEvent.proposed(
            new ProgramEventId("evt-42"), new EventTitle("Czy AI przejmie świat?"), requirements());

        assertThat(first, is(renamedCopy));
    }

    private ProgramEvent proposedEvent() {
        return ProgramEvent.proposed(
            new ProgramEventId("evt-42"),
            new EventTitle("Czy AI przejmie świat?"),
            requirements());
    }

    private RoomRequirements requirements() {
        return new RoomRequirements(40, true, false);
    }

    private TimeSlot slot(int startHour, int startMinute, int endHour, int endMinute) {
        return new TimeSlot(
            LocalDateTime.of(2026, 11, 14, startHour, startMinute),
            LocalDateTime.of(2026, 11, 14, endHour, endMinute));
    }
}

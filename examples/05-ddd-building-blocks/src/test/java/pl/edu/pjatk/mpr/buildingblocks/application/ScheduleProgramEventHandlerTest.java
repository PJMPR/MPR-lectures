package pl.edu.pjatk.mpr.buildingblocks.application;

import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.buildingblocks.domain.event.ProgramEventScheduled;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.EventTitle;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEvent;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEventId;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEventStatus;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomId;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomRequirements;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.TimeSlot;
import pl.edu.pjatk.mpr.buildingblocks.ports.DomainEventPublisher;
import pl.edu.pjatk.mpr.buildingblocks.ports.ProgramEventRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

class ScheduleProgramEventHandlerTest {
    @Test
    void loadsSchedulesSavesAndPublishesDomainEvent() {
        InMemoryEvents events = new InMemoryEvents();
        RecordingPublisher publisher = new RecordingPublisher();
        ProgramEvent event = ProgramEvent.proposed(
            new ProgramEventId("evt-42"),
            new EventTitle("Czy AI przejmie świat?"),
            new RoomRequirements(40, true, false));
        event.confirmSpeaker();
        events.save(event);
        TimeSlot slot = new TimeSlot(
            LocalDateTime.of(2026, 11, 14, 10, 0),
            LocalDateTime.of(2026, 11, 14, 11, 0));
        ScheduleProgramEventHandler handler = new ScheduleProgramEventHandler(events, publisher);

        handler.handle(new ScheduleProgramEvent(event.id(), new RoomId("A-17"), slot));

        ProgramEvent saved = events.findById(event.id()).orElseThrow(AssertionError::new);
        assertThat(saved.status(), is(ProgramEventStatus.SCHEDULED));
        assertThat(publisher.published.size(), is(1));
        assertThat(publisher.published.get(0), instanceOf(ProgramEventScheduled.class));
    }

    private static final class InMemoryEvents implements ProgramEventRepository {
        private final Map<ProgramEventId, ProgramEvent> storage = new HashMap<>();

        @Override public Optional<ProgramEvent> findById(ProgramEventId id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override public void save(ProgramEvent event) {
            storage.put(event.id(), event);
        }
    }

    private static final class RecordingPublisher implements DomainEventPublisher {
        private final List<Object> published = new ArrayList<>();

        @Override public void publish(Object event) {
            published.add(event);
        }
    }
}

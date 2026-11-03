package pl.edu.pjatk.mpr.buildingblocks.application;

import pl.edu.pjatk.mpr.buildingblocks.domain.event.ProgramEventScheduled;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.ProgramEvent;
import pl.edu.pjatk.mpr.buildingblocks.ports.DomainEventPublisher;
import pl.edu.pjatk.mpr.buildingblocks.ports.ProgramEventRepository;

import java.util.Objects;

public final class ScheduleProgramEventHandler {
    private final ProgramEventRepository events;
    private final DomainEventPublisher eventPublisher;

    public ScheduleProgramEventHandler(ProgramEventRepository events, DomainEventPublisher eventPublisher) {
        this.events = Objects.requireNonNull(events, "events");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "eventPublisher");
    }

    public void handle(ScheduleProgramEvent command) {
        ProgramEvent event = events.findById(command.eventId())
            .orElseThrow(() -> new IllegalArgumentException("Program event not found: " + command.eventId()));

        event.scheduleIn(command.roomId(), command.timeSlot());
        events.save(event);
        eventPublisher.publish(new ProgramEventScheduled(event.id(), command.roomId(), command.timeSlot()));
    }
}

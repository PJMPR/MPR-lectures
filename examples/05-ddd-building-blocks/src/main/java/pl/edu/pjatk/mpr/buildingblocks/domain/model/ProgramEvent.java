package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import pl.edu.pjatk.mpr.buildingblocks.domain.exception.DomainRuleViolation;

import java.util.Objects;
import java.util.Optional;

public final class ProgramEvent {
    private final ProgramEventId id;
    private EventTitle title;
    private final RoomRequirements requirements;
    private ProgramEventStatus status;
    private Schedule schedule;

    private ProgramEvent(ProgramEventId id, EventTitle title, RoomRequirements requirements) {
        this.id = Objects.requireNonNull(id, "id");
        this.title = Objects.requireNonNull(title, "title");
        this.requirements = Objects.requireNonNull(requirements, "requirements");
        this.status = ProgramEventStatus.PROPOSED;
    }

    public static ProgramEvent proposed(ProgramEventId id, EventTitle title, RoomRequirements requirements) {
        return new ProgramEvent(id, title, requirements);
    }

    public void confirmSpeaker() {
        requireStatus(ProgramEventStatus.PROPOSED, "Only a proposed event can be confirmed");
        status = ProgramEventStatus.CONFIRMED;
    }

    public void scheduleIn(RoomId roomId, TimeSlot timeSlot) {
        requireStatus(ProgramEventStatus.CONFIRMED, "Speaker must be confirmed before scheduling");
        schedule = new Schedule(roomId, timeSlot);
        status = ProgramEventStatus.SCHEDULED;
    }

    public void reschedule(RoomId roomId, TimeSlot timeSlot) {
        requireStatus(ProgramEventStatus.SCHEDULED, "Only a scheduled event can be rescheduled");
        schedule = new Schedule(roomId, timeSlot);
    }

    public void publish() {
        requireStatus(ProgramEventStatus.SCHEDULED, "Only a scheduled event can be published");
        status = ProgramEventStatus.PUBLISHED;
    }

    public void withdraw() {
        if (status == ProgramEventStatus.WITHDRAWN) {
            throw new DomainRuleViolation("Event is already withdrawn");
        }
        status = ProgramEventStatus.WITHDRAWN;
        schedule = null;
    }

    public void rename(EventTitle newTitle) {
        if (status == ProgramEventStatus.WITHDRAWN) {
            throw new DomainRuleViolation("A withdrawn event cannot be renamed");
        }
        title = Objects.requireNonNull(newTitle, "newTitle");
    }

    private void requireStatus(ProgramEventStatus expected, String message) {
        if (status != expected) throw new DomainRuleViolation(message);
    }

    public ProgramEventId id() { return id; }
    public EventTitle title() { return title; }
    public RoomRequirements requirements() { return requirements; }
    public ProgramEventStatus status() { return status; }
    public Optional<Schedule> schedule() { return Optional.ofNullable(schedule); }

    @Override public boolean equals(Object other) {
        return this == other || other instanceof ProgramEvent && id.equals(((ProgramEvent) other).id);
    }

    @Override public int hashCode() { return id.hashCode(); }
}

package pl.edu.pjatk.mpr.aggregates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DaySchedule {
    public enum Status {DRAFT, PUBLISHED}

    private final String id;
    private final LocalDate date;
    private final List<AgendaEntry> entries = new ArrayList<>();
    private Status status = Status.DRAFT;
    private long version;

    public DaySchedule(String id, LocalDate date) {
        this.id = text(id);
        this.date = Objects.requireNonNull(date);
    }

    public void assign(String eventId, String roomId, TimeSlot slot) {
        requireDraft();
        Objects.requireNonNull(slot);
        if (entries.stream().anyMatch(e -> e.eventId().equals(eventId)))
            throw new DomainRuleViolation("Event is already assigned");
        if (entries.stream().anyMatch(e -> e.roomId().equals(roomId) && e.slot().overlaps(slot)))
            throw new DomainRuleViolation("Room conflict");
        entries.add(new AgendaEntry(eventId, roomId, slot));
    }

    public SchedulePublished publish() {
        requireDraft();
        if (entries.isEmpty()) throw new DomainRuleViolation("Empty schedule cannot be published");
        status = Status.PUBLISHED;
        return new SchedulePublished(id, date, entries.size());
    }

    public String id() {
        return id;
    }

    public LocalDate date() {
        return date;
    }

    public Status status() {
        return status;
    }

    public long version() {
        return version;
    }

    public int entryCount() {
        return entries.size();
    }

    private void requireDraft() {
        if (status != Status.DRAFT) throw new DomainRuleViolation("Published schedule cannot be changed");
    }

    private static String text(String v) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Id cannot be blank");
        return v.trim();
    }
}

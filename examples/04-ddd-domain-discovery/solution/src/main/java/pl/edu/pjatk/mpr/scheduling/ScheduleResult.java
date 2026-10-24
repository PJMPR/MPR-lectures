package pl.edu.pjatk.mpr.scheduling;
import java.util.Objects;
public final class ScheduleResult { private final DomainEvent event; public ScheduleResult(DomainEvent event) { this.event = Objects.requireNonNull(event); } public DomainEvent event() { return event; } }

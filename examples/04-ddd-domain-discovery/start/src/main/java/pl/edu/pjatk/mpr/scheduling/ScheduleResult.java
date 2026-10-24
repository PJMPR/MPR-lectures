package pl.edu.pjatk.mpr.scheduling;
public final class ScheduleResult { private final DomainEvent event; public ScheduleResult(DomainEvent event) { this.event = event; } public DomainEvent event() { return event; } }

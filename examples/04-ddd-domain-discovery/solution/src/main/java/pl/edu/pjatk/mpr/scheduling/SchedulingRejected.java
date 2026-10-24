package pl.edu.pjatk.mpr.scheduling;
public final class SchedulingRejected implements DomainEvent { private final String reason; public SchedulingRejected(String reason) { this.reason = reason; } public String reason() { return reason; } }

package pl.edu.pjatk.mpr.aggregates;
import java.time.LocalDateTime; import java.util.Objects;
public final class TimeSlot { private final LocalDateTime start,end; public TimeSlot(LocalDateTime start,LocalDateTime end){this.start=Objects.requireNonNull(start);this.end=Objects.requireNonNull(end);if(!start.isBefore(end))throw new IllegalArgumentException("Start must be before end");} public boolean overlaps(TimeSlot other){return start.isBefore(other.end)&&other.start.isBefore(end);} }

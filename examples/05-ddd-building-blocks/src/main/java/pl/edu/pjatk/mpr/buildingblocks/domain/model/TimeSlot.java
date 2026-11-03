package pl.edu.pjatk.mpr.buildingblocks.domain.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TimeSlot {
    private static final Duration MINIMUM_DURATION = Duration.ofMinutes(15);
    private final LocalDateTime start;
    private final LocalDateTime end;

    public TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
        if (Duration.between(start, end).compareTo(MINIMUM_DURATION) < 0) {
            throw new IllegalArgumentException("Time slot must last at least 15 minutes");
        }
    }

    public LocalDateTime start() { return start; }
    public LocalDateTime end() { return end; }

    public boolean overlaps(TimeSlot other) {
        Objects.requireNonNull(other, "other");
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof TimeSlot)) return false;
        TimeSlot that = (TimeSlot) other;
        return start.equals(that.start) && end.equals(that.end);
    }

    @Override public int hashCode() { return Objects.hash(start, end); }
}

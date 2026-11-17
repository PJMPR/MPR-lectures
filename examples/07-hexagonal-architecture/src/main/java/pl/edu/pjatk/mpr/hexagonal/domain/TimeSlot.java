package pl.edu.pjatk.mpr.hexagonal.domain;

import java.time.LocalTime;
import java.util.Objects;

public final class TimeSlot {
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = Objects.requireNonNull(start);
        this.end = Objects.requireNonNull(end);
        if (!start.isBefore(end)) throw new IllegalArgumentException("Start must be before end");
    }

    public boolean overlaps(TimeSlot other) {
        return start.isBefore(other.end) && other.start.isBefore(end);
    }

    public LocalTime start() { return start; }
    public LocalTime end() { return end; }
}

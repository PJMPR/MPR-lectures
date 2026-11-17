package pl.edu.pjatk.mpr.hexagonal.domain;

import java.time.LocalTime;
import java.util.Objects;

public record TimeSlot(LocalTime start, LocalTime end) {
    public TimeSlot {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        if (!start.isBefore(end)) throw new IllegalArgumentException("Start must be before end");
    }

    public boolean overlaps(TimeSlot other) {
        return start.isBefore(other.end) && other.start.isBefore(end);
    }
}

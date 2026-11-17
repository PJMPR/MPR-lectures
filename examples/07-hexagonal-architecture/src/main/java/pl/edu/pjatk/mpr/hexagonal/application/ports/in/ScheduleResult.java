package pl.edu.pjatk.mpr.hexagonal.application.ports.in;

import java.util.Objects;

import pl.edu.pjatk.mpr.hexagonal.domain.ProgramEventScheduled;

public record ScheduleResult(ProgramEventScheduled event) {
    public ScheduleResult {
        Objects.requireNonNull(event);
    }

    public static ScheduleResult scheduled(ProgramEventScheduled event) {
        return new ScheduleResult(event);
    }

    public boolean scheduled() {
        return true;
    }

}

package pl.edu.pjatk.mpr.hexagonal.domain;

public final class SchedulingConflict extends RuntimeException {
    public SchedulingConflict(String message) {
        super(message);
    }
}

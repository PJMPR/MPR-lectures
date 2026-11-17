package pl.edu.pjatk.mpr.hexagonal.application.ports.in;

public interface ScheduleProgramEvent {
    ScheduleResult handle(ScheduleProgramEventCommand command);
}

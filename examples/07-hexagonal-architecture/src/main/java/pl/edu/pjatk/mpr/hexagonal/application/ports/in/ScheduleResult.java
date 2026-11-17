package pl.edu.pjatk.mpr.hexagonal.application.ports.in;

import pl.edu.pjatk.mpr.hexagonal.domain.ProgramEventScheduled;

public final class ScheduleResult {
    private final ProgramEventScheduled event;
    private ScheduleResult(ProgramEventScheduled event){this.event=event;}
    public static ScheduleResult scheduled(ProgramEventScheduled event){return new ScheduleResult(event);}
    public boolean scheduled(){return true;} public ProgramEventScheduled event(){return event;}
}

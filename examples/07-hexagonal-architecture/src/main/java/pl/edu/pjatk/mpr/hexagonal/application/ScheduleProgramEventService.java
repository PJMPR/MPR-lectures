package pl.edu.pjatk.mpr.hexagonal.application;

import pl.edu.pjatk.mpr.hexagonal.application.ports.in.*;
import pl.edu.pjatk.mpr.hexagonal.application.ports.out.*;
import pl.edu.pjatk.mpr.hexagonal.domain.*;

public final class ScheduleProgramEventService implements ScheduleProgramEvent {
    private final DayScheduleRepository schedules; private final ParticipantNotifier notifier;
    public ScheduleProgramEventService(DayScheduleRepository schedules, ParticipantNotifier notifier){this.schedules=schedules;this.notifier=notifier;}
    public ScheduleResult handle(ScheduleProgramEventCommand command){
        DaySchedule schedule=schedules.findByDate(command.date()).orElseGet(()->DaySchedule.empty(command.date()));
        ProgramEventScheduled event=schedule.schedule(command.eventId(),command.roomId(),command.timeSlot());
        schedules.save(schedule); notifier.programEventScheduled(event); return ScheduleResult.scheduled(event);
    }
}

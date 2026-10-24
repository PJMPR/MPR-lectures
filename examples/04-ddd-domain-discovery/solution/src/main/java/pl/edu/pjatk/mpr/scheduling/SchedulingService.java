package pl.edu.pjatk.mpr.scheduling;
import java.util.ArrayList; import java.util.List; import java.util.UUID;
public final class SchedulingService {
    private final List<ProgramEventScheduled> scheduled = new ArrayList<>();
    public ScheduleResult schedule(ScheduleProgramEventCommand command) {
        if (command.roomId() == null || command.roomId().isBlank()) return rejected("Program event requires a room");
        for (ProgramEventScheduled existing : scheduled) {
            if (!overlaps(command, existing)) continue;
            if (existing.roomId().equals(command.roomId())) return rejected("Room is already occupied");
            if (existing.hostId().equals(command.hostId())) return rejected("Host has another program event");
        }
        ProgramEventScheduled event = new ProgramEventScheduled(UUID.randomUUID().toString(), command.title(), command.hostId(), command.roomId(), command.startsAt(), command.endsAt());
        scheduled.add(event); return new ScheduleResult(event);
    }
    private boolean overlaps(ScheduleProgramEventCommand candidate, ProgramEventScheduled existing) { return candidate.startsAt() < existing.endsAt() && candidate.endsAt() > existing.startsAt(); }
    private ScheduleResult rejected(String reason) { return new ScheduleResult(new SchedulingRejected(reason)); }
}

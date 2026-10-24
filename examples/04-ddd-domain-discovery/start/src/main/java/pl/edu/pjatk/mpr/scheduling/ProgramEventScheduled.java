package pl.edu.pjatk.mpr.scheduling;
public final class ProgramEventScheduled implements DomainEvent {
    private final String programEventId, title, hostId, roomId; private final int startsAt, endsAt;
    public ProgramEventScheduled(String programEventId, String title, String hostId, String roomId, int startsAt, int endsAt) {
        this.programEventId = programEventId; this.title = title; this.hostId = hostId; this.roomId = roomId; this.startsAt = startsAt; this.endsAt = endsAt;
    }
    public String programEventId() { return programEventId; } public String title() { return title; } public String hostId() { return hostId; }
    public String roomId() { return roomId; } public int startsAt() { return startsAt; } public int endsAt() { return endsAt; }
}

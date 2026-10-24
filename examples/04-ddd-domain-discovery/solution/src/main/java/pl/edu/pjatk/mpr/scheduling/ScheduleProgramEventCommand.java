package pl.edu.pjatk.mpr.scheduling;
import java.util.ArrayList; import java.util.Collections; import java.util.List; import java.util.Objects;
public final class ScheduleProgramEventCommand {
    private final String title, hostId, roomId; private final int startsAt, endsAt; private final List<String> requiredEquipment;
    public ScheduleProgramEventCommand(String title, String hostId, String roomId, int startsAt, int endsAt, List<String> requiredEquipment) {
        this.title = Objects.requireNonNull(title); this.hostId = Objects.requireNonNull(hostId); this.roomId = roomId;
        this.startsAt = startsAt; this.endsAt = endsAt; this.requiredEquipment = Collections.unmodifiableList(new ArrayList<>(requiredEquipment));
    }
    public String title() { return title; } public String hostId() { return hostId; } public String roomId() { return roomId; }
    public int startsAt() { return startsAt; } public int endsAt() { return endsAt; } public List<String> requiredEquipment() { return requiredEquipment; }
}

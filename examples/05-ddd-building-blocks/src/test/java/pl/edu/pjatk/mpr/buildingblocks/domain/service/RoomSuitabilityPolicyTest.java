package pl.edu.pjatk.mpr.buildingblocks.domain.service;

import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.Room;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomId;
import pl.edu.pjatk.mpr.buildingblocks.domain.model.RoomRequirements;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoomSuitabilityPolicyTest {
    private final RoomSuitabilityPolicy policy = new RoomSuitabilityPolicy();

    @Test
    void acceptsRoomMeetingAllRequirements() {
        RoomRequirements requirements = new RoomRequirements(40, true, true);
        Room room = new Room(new RoomId("A-17"), 60, true, true);

        assertTrue(policy.isSuitable(requirements, room));
    }

    @Test
    void rejectsRoomThatIsTooSmall() {
        RoomRequirements requirements = new RoomRequirements(40, false, false);
        Room room = new Room(new RoomId("B-04"), 20, true, true);

        assertFalse(policy.isSuitable(requirements, room));
    }

    @Test
    void rejectsRoomWithoutRequiredEquipment() {
        RoomRequirements requirements = new RoomRequirements(20, true, false);
        Room room = new Room(new RoomId("C-01"), 50, false, false);

        assertFalse(policy.isSuitable(requirements, room));
    }
}

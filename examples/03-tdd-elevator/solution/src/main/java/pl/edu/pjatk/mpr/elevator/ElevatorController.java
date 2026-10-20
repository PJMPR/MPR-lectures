package pl.edu.pjatk.mpr.elevator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public final class ElevatorController {
    private final FloorSensor floorSensor;
    private final LoadSensor loadSensor;
    private final DoorController doors;
    private final ElevatorMotor motor;
    private final AlarmSystem alarm;
    private final Queue<Integer> requests = new ArrayDeque<>();

    public ElevatorController(FloorSensor floorSensor, LoadSensor loadSensor,
                              DoorController doors, ElevatorMotor motor,
                              AlarmSystem alarm) {
        this.floorSensor = floorSensor;
        this.loadSensor = loadSensor;
        this.doors = doors;
        this.motor = motor;
        this.alarm = alarm;
    }

    public void requestFloor(int floor) {
        if (floor < 0) throw new IllegalArgumentException("Floor must not be negative");
        requests.add(floor);
    }

    public RideResult serveNext() {
        if (requests.isEmpty()) throw new IllegalStateException("No floor requested");
        int destination = requests.peek();
        int current = floorSensor.currentFloor();

        if (current == destination) {
            requests.remove();
            return new RideResult(RideStatus.ALREADY_THERE, current, destination);
        }
        if (loadSensor.isOverloaded()) {
            alarm.signal("OVERLOAD");
            return new RideResult(RideStatus.BLOCKED, current, destination);
        }

        doors.close();
        if (!doors.isClosed()) {
            alarm.signal("DOOR_OPEN");
            return new RideResult(RideStatus.BLOCKED, current, destination);
        }

        Direction direction = destination > current ? Direction.UP : Direction.DOWN;
        motor.move(direction, destination);
        requests.remove();
        return new RideResult(RideStatus.MOVING, current, destination);
    }

    public List<Integer> pendingFloors() {
        return Collections.unmodifiableList(new ArrayList<>(requests));
    }
}

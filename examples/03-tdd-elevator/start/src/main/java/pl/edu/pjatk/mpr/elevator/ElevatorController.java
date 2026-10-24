package pl.edu.pjatk.mpr.elevator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public final class ElevatorController {
    private final FloorSensor floorSensor;

    private Queue<Integer> requestedFloors = new ArrayDeque<>();

    private final LoadSensor loadSensor;
    private final DoorController doors;
    private final ElevatorMotor motor;
    private final AlarmSystem alarm;

    public ElevatorController(FloorSensor floorSensor, LoadSensor loadSensor,
                              DoorController doors, ElevatorMotor motor,
                              AlarmSystem alarm) {
        // Celowo pusto. KROK 1 pokaże, które zależności trzeba zachować w polach.
        this.floorSensor = floorSensor;
        this.loadSensor = loadSensor;
        this.doors = doors;
        this.motor = motor;
        this.alarm = alarm;
    }

    public void requestFloor(int floor) {
        this.requestedFloors.add(floor);
    }

    public RideResult serveNext() {
        if(requestedFloors.isEmpty()){
            throw new IllegalStateException("No floor requested");
        }

        var requestedFloor = requestedFloors.peek();
        var currentFloor = floorSensor.currentFloor();
        if(requestedFloor == currentFloor) {
            return new RideResult(RideStatus.ALREADY_THERE, requestedFloor, currentFloor);
        }

        if(requestedFloor != currentFloor){
            if(loadSensor.isOverloaded()){
                alarm.signal("OVERLOAD");
                return new RideResult(RideStatus.BLOCKED, currentFloor, requestedFloor);
            }
            doors.close();
            if(doors.isClosed()){
                if(requestedFloor > currentFloor){
                    motor.move(Direction.UP, requestedFloor);
                    requestedFloors.remove();
                    return new RideResult(RideStatus.MOVING, currentFloor, requestedFloor);
                }
                if(requestedFloor < currentFloor){
                    motor.move(Direction.DOWN, requestedFloor);
                    requestedFloors.remove(); 
                    return new RideResult(RideStatus.MOVING, currentFloor, requestedFloor);
                }
            }else{
                alarm.signal("DOOR_OPEN");
                return new RideResult(RideStatus.BLOCKED, currentFloor, requestedFloor);
            }
        }
        return new RideResult(RideStatus.BLOCKED, currentFloor, requestedFloor);
    }

    public List<Integer> pendingFloors() {
        return new ArrayList<>(requestedFloors);
    }
}

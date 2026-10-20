package pl.edu.pjatk.mpr.elevator;

import java.util.List;

public final class ElevatorController {
    public ElevatorController(FloorSensor floorSensor, LoadSensor loadSensor,
                              DoorController doors, ElevatorMotor motor,
                              AlarmSystem alarm) {
        // Celowo pusto. KROK 1 pokaże, które zależności trzeba zachować w polach.
    }

    public void requestFloor(int floor) {
    }

    public RideResult serveNext() {
        // Celowa atrapa: pierwszy włączony test ma być czerwony.
        throw new UnsupportedOperationException("Start with the first failing test");
    }

    public List<Integer> pendingFloors() {
        // Ten szczegół pojawi się dopiero w ostatniej iteracji.
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

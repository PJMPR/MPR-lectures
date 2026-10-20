package pl.edu.pjatk.mpr.elevator;

public final class RideResult {
    private final RideStatus status;
    private final int fromFloor;
    private final int destinationFloor;

    public RideResult(RideStatus status, int fromFloor, int destinationFloor) {
        this.status = status;
        this.fromFloor = fromFloor;
        this.destinationFloor = destinationFloor;
    }

    public RideStatus getStatus() { return status; }
    public int getFromFloor() { return fromFloor; }
    public int getDestinationFloor() { return destinationFloor; }
}

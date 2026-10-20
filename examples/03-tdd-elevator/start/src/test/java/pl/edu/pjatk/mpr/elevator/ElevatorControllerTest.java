package pl.edu.pjatk.mpr.elevator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElevatorControllerTest {
    @Mock FloorSensor floorSensor;
    @Mock LoadSensor loadSensor;
    @Mock DoorController doors;
    @Mock ElevatorMotor motor;
    @Mock AlarmSystem alarm;
    ElevatorController controller;

    @BeforeEach
    void setUp() {
        controller = new ElevatorController(floorSensor, loadSensor, doors, motor, alarm);
    }

    // KROK 1 — RED: usuń @Disabled tylko tutaj. Test powinien najpierw zakończyć
    // się UnsupportedOperationException. W GREEN zachowaj FloorSensor w polu,
    // dodaj najprostszą kolejkę i obsłuż wyłącznie przypadek current == target.
    // REFACTOR: nazwij wynik domenowo; nie uruchamiaj jeszcze żadnego urządzenia.
    @Disabled("KROK 1: włącz jako pierwszy")
    @Test
    void floorAlreadyReached_doesNotStartAnyDevice() {
        when(floorSensor.currentFloor()).thenReturn(3);
        controller.requestFloor(3);

        RideResult result = controller.serveNext();

        assertThat(result.getStatus(), is(RideStatus.ALREADY_THERE));
        assertThat(result.getFromFloor(), is(3));
        assertThat(result.getDestinationFloor(), is(3));
        verifyNoInteractions(doors, motor, alarm);
    }

    // KROK 2 — RED: winda ma jechać w górę, ale dopiero po zamknięciu drzwi.
    // GREEN: zachowaj potrzebne zależności, zamknij drzwi i wydaj komendę silnikowi.
    // InOrder nie jest tu ozdobą: kolejność stanowi wymaganie bezpieczeństwa.
    @Disabled("KROK 2: włącz po zielonym kroku 1")
    @Test
    void floorAbove_closesDoorsBeforeMovingUp() {
        when(floorSensor.currentFloor()).thenReturn(2);
        when(doors.isClosed()).thenReturn(true);
        controller.requestFloor(7);

        RideResult result = controller.serveNext();

        assertThat(result.getStatus(), is(RideStatus.MOVING));
        assertThat(result.getDestinationFloor(), is(7));
        InOrder sequence = inOrder(doors, motor);
        sequence.verify(doors).close();
        sequence.verify(doors).isClosed();
        sequence.verify(motor).move(Direction.UP, 7);
    }

    // KROK 3 — nowy przykład różni się tylko kierunkiem. Najpierw możesz użyć
    // prostego if. Dopiero przy GREEN zdecyduj, czy warto wydzielić obliczanie kierunku.
    @Disabled("KROK 3: włącz po zielonym kroku 2")
    @Test
    void floorBelow_movesDown() {
        when(floorSensor.currentFloor()).thenReturn(8);
        when(doors.isClosed()).thenReturn(true);
        controller.requestFloor(4);

        RideResult result = controller.serveNext();

        assertThat(result.getStatus(), is(RideStatus.MOVING));
        verify(motor).move(Direction.DOWN, 4);
    }

    // KROK 4 — drzwi zgłaszają false. Silnik nie może otrzymać żadnej komendy,
    // alarm dostaje kod domenowy, a zgłoszenie zostaje w kolejce do ponowienia.
    @Disabled("KROK 4: włącz po zielonym kroku 3")
    @Test
    void doorsStayOpen_blocksRideAndRaisesAlarm() {
        when(floorSensor.currentFloor()).thenReturn(2);
        when(doors.isClosed()).thenReturn(false);
        controller.requestFloor(7);

        RideResult result = controller.serveNext();

        assertThat(result.getStatus(), is(RideStatus.BLOCKED));
        assertThat(controller.pendingFloors(), contains(7));
        verify(alarm).signal("DOOR_OPEN");
        verifyNoInteractions(motor);
    }

    // KROK 5 — przeciążenie sprawdzamy przed drzwiami. To dobry przykład stubowania
    // odczytu i weryfikacji braku niedozwolonych efektów ubocznych.
    @Disabled("KROK 5: włącz po zielonym kroku 4")
    @Test
    void overloadedElevator_doesNotCloseDoorsOrMove() {
        when(floorSensor.currentFloor()).thenReturn(2);
        when(loadSensor.isOverloaded()).thenReturn(true);
        controller.requestFloor(7);

        RideResult result = controller.serveNext();

        assertThat(result.getStatus(), is(RideStatus.BLOCKED));
        assertThat(controller.pendingFloors(), contains(7));
        verify(alarm).signal("OVERLOAD");
        verifyNoInteractions(doors, motor);
    }

    // KROK 6 — dwa zgłoszenia ujawniają politykę FIFO. Po pierwszej jeździe w kolejce
    // zostaje tylko 7. Zwracaj niemodyfikowalną kopię zamiast wewnętrznej kolekcji.
    @Disabled("KROK 6: włącz na końcu")
    @Test
    void multipleRequests_areServedInFifoOrder() {
        when(floorSensor.currentFloor()).thenReturn(1, 3);
        when(doors.isClosed()).thenReturn(true);
        controller.requestFloor(3);
        controller.requestFloor(7);

        RideResult first = controller.serveNext();

        assertThat(first.getDestinationFloor(), is(3));
        assertThat(controller.pendingFloors(), contains(7));

        RideResult second = controller.serveNext();
        assertThat(second.getDestinationFloor(), is(7));
        assertThat(controller.pendingFloors(), empty());
    }
}

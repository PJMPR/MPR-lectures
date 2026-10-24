package pl.edu.pjatk.mpr.solid.shipping;

// KROK 3 (LSP): podtyp dziedziczy operację, której nie potrafi obsłużyć.
public class ShippingMethod {
  public void deliverTo(String address) {}
}

final class ParcelLockerShipping extends ShippingMethod {
  @Override
  public void deliverTo(String address) {
    throw new UnsupportedOperationException("Use locker id");
  }
}

// Propozycja rozwiązania: opisz dwie prawdziwe, rozłączne obietnice.
// interface HomeDelivery {
//   Delivery quote(Address destination);
// }
//
// interface PickupDelivery {
//   Delivery quote(LockerId locker);
// }
//
// final class CourierShipping implements HomeDelivery { ... }
// final class ParcelLockerShipping implements PickupDelivery { ... }

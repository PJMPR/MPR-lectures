package pl.edu.pjatk.mpr.solid.shipping;

import java.math.BigDecimal;
import java.util.Objects;

interface HomeDelivery {
  Delivery quote(Address destination);
}

interface PickupDelivery {
  Delivery quote(LockerId locker);
}

final class CourierShipping implements HomeDelivery {
  public Delivery quote(Address destination) {
    Objects.requireNonNull(destination);
    return new Delivery(new BigDecimal("15.00"));
  }
}

final class ParcelLockerShipping implements PickupDelivery {
  public Delivery quote(LockerId locker) {
    Objects.requireNonNull(locker);
    return new Delivery(new BigDecimal("9.99"));
  }
}

final class Address {
  private final String value;

  Address(String value) {
    this.value = Objects.requireNonNull(value);
  }

  String getValue() {
    return value;
  }
}

final class LockerId {
  private final String value;

  LockerId(String value) {
    this.value = Objects.requireNonNull(value);
  }

  String getValue() {
    return value;
  }
}

final class Delivery {
  private final BigDecimal price;

  Delivery(BigDecimal price) {
    this.price = Objects.requireNonNull(price);
  }

  BigDecimal getPrice() {
    return price;
  }
}

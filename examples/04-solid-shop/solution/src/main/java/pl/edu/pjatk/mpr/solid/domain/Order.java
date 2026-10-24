package pl.edu.pjatk.mpr.solid.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
  private final String id;
  private final BigDecimal total;

  public Order(String id, BigDecimal total) {
    this.id = Objects.requireNonNull(id);
    this.total = Objects.requireNonNull(total);
    if (total.signum() < 0) throw new IllegalArgumentException("Total cannot be negative");
  }

  public String getId() {
    return id;
  }

  public BigDecimal getTotal() {
    return total;
  }
}

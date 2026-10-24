package pl.edu.pjatk.mpr.solid.domain;

import java.math.BigDecimal;
import java.util.Objects;

public final class Receipt {
  private final String orderId;
  private final BigDecimal amount;

  public Receipt(String orderId, BigDecimal amount) {
    this.orderId = Objects.requireNonNull(orderId);
    this.amount = Objects.requireNonNull(amount);
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}

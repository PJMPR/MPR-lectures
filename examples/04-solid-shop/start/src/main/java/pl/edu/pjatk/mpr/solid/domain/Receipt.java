package pl.edu.pjatk.mpr.solid.domain;

import java.math.BigDecimal;

public final class Receipt {
  private final String orderId;
  private final BigDecimal amount;

  public Receipt(String orderId, BigDecimal amount) {
    this.orderId = orderId;
    this.amount = amount;
  }

  public String getOrderId() {
    return orderId;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}

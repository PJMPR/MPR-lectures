package pl.edu.pjatk.mpr.solid.pricing;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;

public final class VipDiscount implements DiscountPolicy {
  private static final BigDecimal MULTIPLIER = new BigDecimal("0.90");

  @Override
  public BigDecimal priceFor(Order order, Customer customer) {
    return order.getTotal().multiply(MULTIPLIER);
  }
}

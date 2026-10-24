package pl.edu.pjatk.mpr.solid.pricing;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;

public final class RegularPrice implements DiscountPolicy {
  @Override
  public BigDecimal priceFor(Order order, Customer customer) {
    return order.getTotal();
  }
}

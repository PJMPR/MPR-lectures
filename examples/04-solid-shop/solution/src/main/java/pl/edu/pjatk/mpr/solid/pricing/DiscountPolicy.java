package pl.edu.pjatk.mpr.solid.pricing;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;

public interface DiscountPolicy {
  BigDecimal priceFor(Order order, Customer customer);
}

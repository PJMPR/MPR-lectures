package pl.edu.pjatk.mpr.solid.pricing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;

class DiscountPolicyContractTest {
  private final Order order = new Order("ORD-1", new BigDecimal("100.00"));
  private final Customer customer = new Customer("ada@example.com");

  @Test
  void everyPolicyReturnsPriceWithinTheContract() {
    for (DiscountPolicy policy : new DiscountPolicy[] {new RegularPrice(), new VipDiscount()}) {
      BigDecimal price = policy.priceFor(order, customer);

      assertThat(price, greaterThanOrEqualTo(BigDecimal.ZERO));
      assertThat(price, lessThanOrEqualTo(order.getTotal()));
    }
  }
}

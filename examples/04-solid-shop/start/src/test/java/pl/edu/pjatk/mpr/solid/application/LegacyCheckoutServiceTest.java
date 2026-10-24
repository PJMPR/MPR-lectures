package pl.edu.pjatk.mpr.solid.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;
import pl.edu.pjatk.mpr.solid.domain.Receipt;

class LegacyCheckoutServiceTest {
  @Test
  void vipCheckout_preservesObservableBehaviourDuringRefactoring() {
    LegacyCheckoutService service = new LegacyCheckoutService();
    Order order = new Order("ORD-42", new BigDecimal("100.00"));
    Customer customer = new Customer("ada@example.com", Customer.Type.VIP);
    Receipt receipt = service.checkout(order, customer);
    assertThat(receipt.getAmount(), comparesEqualTo(new BigDecimal("90.00")));
    assertThat(service.getLastSavedOrder(), containsString("ORD-42/90.0000"));
    assertThat(service.getLastNotification(), containsString("ada@example.com"));
  }
}

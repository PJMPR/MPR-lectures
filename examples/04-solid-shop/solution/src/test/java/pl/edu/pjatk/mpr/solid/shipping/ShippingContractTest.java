package pl.edu.pjatk.mpr.solid.shipping;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ShippingContractTest {
  @Test
  void eachShippingTypeOnlyPromisesSupportedDelivery() {
    assertThat(
        new CourierShipping().quote(new Address("Gdańsk")).getPrice(),
        comparesEqualTo(new BigDecimal("15.00")));
    assertThat(
        new ParcelLockerShipping().quote(new LockerId("GDA01")).getPrice(),
        comparesEqualTo(new BigDecimal("9.99")));
  }
}

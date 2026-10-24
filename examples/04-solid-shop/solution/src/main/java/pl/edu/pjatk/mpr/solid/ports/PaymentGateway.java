package pl.edu.pjatk.mpr.solid.ports;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;

public interface PaymentGateway {
  void charge(Customer customer, BigDecimal amount);
}

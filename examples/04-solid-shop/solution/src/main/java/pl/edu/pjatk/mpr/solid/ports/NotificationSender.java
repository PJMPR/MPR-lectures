package pl.edu.pjatk.mpr.solid.ports;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;

public interface NotificationSender {
  void sendReceipt(Customer customer, Order order, BigDecimal amount);
}

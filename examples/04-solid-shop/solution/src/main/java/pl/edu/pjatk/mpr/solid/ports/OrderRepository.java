package pl.edu.pjatk.mpr.solid.ports;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Order;

public interface OrderRepository {
  void save(Order order, BigDecimal paidAmount);
}

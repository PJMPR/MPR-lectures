package pl.edu.pjatk.mpr.orders;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentResult charge(String orderId, BigDecimal amount);
}

package pl.edu.pjatk.mpr.solid.application;

import java.math.BigDecimal;
import java.util.Objects;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;
import pl.edu.pjatk.mpr.solid.domain.Receipt;
import pl.edu.pjatk.mpr.solid.ports.NotificationSender;
import pl.edu.pjatk.mpr.solid.ports.OrderRepository;
import pl.edu.pjatk.mpr.solid.ports.PaymentGateway;
import pl.edu.pjatk.mpr.solid.pricing.DiscountPolicy;

public final class CheckoutService {
  private final DiscountPolicy discountPolicy;
  private final PaymentGateway paymentGateway;
  private final OrderRepository orderRepository;
  private final NotificationSender notificationSender;

  public CheckoutService(
      DiscountPolicy discountPolicy,
      PaymentGateway paymentGateway,
      OrderRepository orderRepository,
      NotificationSender notificationSender) {
    this.discountPolicy = Objects.requireNonNull(discountPolicy);
    this.paymentGateway = Objects.requireNonNull(paymentGateway);
    this.orderRepository = Objects.requireNonNull(orderRepository);
    this.notificationSender = Objects.requireNonNull(notificationSender);
  }

  public Receipt checkout(Order order, Customer customer) {
    BigDecimal amount = discountPolicy.priceFor(order, customer);
    paymentGateway.charge(customer, amount);
    orderRepository.save(order, amount);
    notificationSender.sendReceipt(customer, order, amount);
    return new Receipt(order.getId(), amount);
  }
}

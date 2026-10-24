package pl.edu.pjatk.mpr.solid.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;
import pl.edu.pjatk.mpr.solid.domain.Receipt;
import pl.edu.pjatk.mpr.solid.ports.NotificationSender;
import pl.edu.pjatk.mpr.solid.ports.OrderRepository;
import pl.edu.pjatk.mpr.solid.ports.PaymentGateway;
import pl.edu.pjatk.mpr.solid.pricing.DiscountPolicy;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {
  @Mock DiscountPolicy discount;
  @Mock PaymentGateway payment;
  @Mock OrderRepository repository;
  @Mock NotificationSender notification;
  private CheckoutService service;
  private Order order;
  private Customer customer;

  @BeforeEach
  void setUp() {
    service = new CheckoutService(discount, payment, repository, notification);
    order = new Order("ORD-42", money("100.00"));
    customer = new Customer("ada@example.com");
  }

  @Test
  void checkout_usesPolicyAndPassesTheSameAmountToCollaborators() {
    when(discount.priceFor(order, customer)).thenReturn(money("90.00"));
    Receipt receipt = service.checkout(order, customer);
    assertThat(receipt.getAmount(), comparesEqualTo(money("90.00")));
    verify(payment).charge(customer, money("90.00"));
    verify(repository).save(order, money("90.00"));
    verify(notification).sendReceipt(customer, order, money("90.00"));
  }

  @Test
  void failedPayment_stopsPersistenceAndNotification() {
    when(discount.priceFor(order, customer)).thenReturn(money("90.00"));
    doThrow(new IllegalStateException("Rejected")).when(payment).charge(customer, money("90.00"));
    org.junit.jupiter.api.Assertions.assertThrows(
        IllegalStateException.class, () -> service.checkout(order, customer));
    verifyNoInteractions(repository, notification);
  }

  private static BigDecimal money(String value) {
    return new BigDecimal(value);
  }
}

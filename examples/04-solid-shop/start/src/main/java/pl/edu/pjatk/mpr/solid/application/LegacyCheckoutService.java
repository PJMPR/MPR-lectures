package pl.edu.pjatk.mpr.solid.application;

import java.math.BigDecimal;
import pl.edu.pjatk.mpr.solid.domain.Customer;
import pl.edu.pjatk.mpr.solid.domain.Order;
import pl.edu.pjatk.mpr.solid.domain.Receipt;

public final class LegacyCheckoutService {
  private String lastSavedOrder;
  private String lastNotification;

  public Receipt checkout(Order order, Customer customer) {
    // KROK 1 (SRP): obliczenia, zapis, płatność i komunikacja są w jednej klasie.
    // Propozycja: wydziel osobne role i pozostaw tutaj wyłącznie orkiestrację:
    // private final DiscountPolicy discountPolicy;
    // private final PaymentGateway paymentGateway;
    // private final OrderRepository orderRepository;
    // private final NotificationSender notificationSender;
    BigDecimal multiplier;
    // KROK 2 (OCP): każdy nowy typ klienta rozszerza ten warunek.
    // Propozycja: zastąp switch strategią przekazaną do konstruktora:
    // BigDecimal amount = discountPolicy.priceFor(order, customer);
    switch (customer.getType()) {
      case VIP:
        multiplier = new BigDecimal("0.90");
        break;
      case EMPLOYEE:
        multiplier = new BigDecimal("0.70");
        break;
      default:
        multiplier = BigDecimal.ONE;
    }
    BigDecimal amount = order.getTotal().multiply(multiplier);
    // KROK 5 (DIP): logika biznesowa symuluje konkretne systemy techniczne.
    // Propozycja: uzależnij przypadek użycia od portów, nie od Stripe/SQL/SMTP:
    // paymentGateway.charge(customer, amount);
    // orderRepository.save(order, amount);
    // notificationSender.sendReceipt(customer, order, amount);
    chargeWithStripe(customer.getEmail(), amount);
    lastSavedOrder = "SQL:orders/" + order.getId() + "/" + amount;
    lastNotification = "SMTP:" + customer.getEmail() + ":Paid " + amount;
    return new Receipt(order.getId(), amount);
  }

  private void chargeWithStripe(String account, BigDecimal amount) {
    if (account.isBlank() || amount.signum() < 0) throw new IllegalArgumentException();
  }

  public String getLastSavedOrder() {
    return lastSavedOrder;
  }

  public String getLastNotification() {
    return lastNotification;
  }
}

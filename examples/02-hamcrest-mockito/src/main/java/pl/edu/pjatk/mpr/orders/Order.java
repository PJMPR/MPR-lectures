package pl.edu.pjatk.mpr.orders;

import java.math.BigDecimal;
import java.util.Objects;

public final class Order {
    private final String id;
    private final String customerEmail;
    private final BigDecimal total;
    private OrderStatus status;

    public Order(String id, String customerEmail, BigDecimal total) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Order id is required");
        }
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        if (total == null || total.signum() <= 0) {
            throw new IllegalArgumentException("Order total must be positive");
        }
        this.id = id;
        this.customerEmail = customerEmail;
        this.total = total;
        this.status = OrderStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void markAsPaid() {
        status = OrderStatus.PAID;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Order)) {
            return false;
        }
        Order order = (Order) object;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package pl.edu.pjatk.mpr.orders;

public final class PaymentRejectedException extends RuntimeException {
    public PaymentRejectedException(String orderId) {
        super("Payment rejected for order " + orderId);
    }
}

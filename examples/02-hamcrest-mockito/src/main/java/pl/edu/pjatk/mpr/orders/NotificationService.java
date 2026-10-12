package pl.edu.pjatk.mpr.orders;

public interface NotificationService {
    void sendOrderConfirmation(String email, String orderId);
}

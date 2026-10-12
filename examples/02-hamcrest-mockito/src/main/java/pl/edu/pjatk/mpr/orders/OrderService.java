package pl.edu.pjatk.mpr.orders;

public final class OrderService {
    private final OrderRepository repository;
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;

    public OrderService(OrderRepository repository,
                        PaymentGateway paymentGateway,
                        NotificationService notificationService) {
        this.repository = repository;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    public Order placeOrder(Order order) {
        PaymentResult payment = paymentGateway.charge(order.getId(), order.getTotal());
        if (!payment.isSuccessful()) {
            throw new PaymentRejectedException(order.getId());
        }

        order.markAsPaid();
        Order savedOrder = repository.save(order);
        notificationService.sendOrderConfirmation(
                savedOrder.getCustomerEmail(), savedOrder.getId());
        return savedOrder;
    }
}

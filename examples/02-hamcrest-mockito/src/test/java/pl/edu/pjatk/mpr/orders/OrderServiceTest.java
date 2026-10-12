package pl.edu.pjatk.mpr.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static pl.edu.pjatk.mpr.orders.PaidOrderMatcher.paidOrderWithId;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderService service;

    @Test
    void placeOrder_paymentApproved_savesPaidOrderAndSendsConfirmation() {
        Order order = order("ORD-101", "ada@example.com", "149.90");
        when(paymentGateway.charge("ORD-101", new BigDecimal("149.90")))
                .thenReturn(PaymentResult.approved("TX-777"));
        when(repository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = service.placeOrder(order);

        assertThat(result, sameInstance(order));
        assertThat(result, is(paidOrderWithId("ORD-101")));

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue(), is(paidOrderWithId("ORD-101")));
        verify(notificationService)
                .sendOrderConfirmation("ada@example.com", "ORD-101");
    }

    @Test
    void placeOrder_paymentRejected_doesNotSaveOrNotify() {
        Order order = order("ORD-102", "jan@example.com", "29.00");
        when(paymentGateway.charge(eq("ORD-102"), any(BigDecimal.class)))
                .thenReturn(PaymentResult.declined());

        PaymentRejectedException exception = assertThrows(
                PaymentRejectedException.class,
                () -> service.placeOrder(order));

        assertThat(exception.getMessage(), is("Payment rejected for order ORD-102"));
        verify(repository, never()).save(any());
        verifyNoInteractions(notificationService);
    }

    @Test
    void placeOrder_callsDependenciesInBusinessOrder() {
        Order order = order("ORD-103", "ola@example.com", "80.00");
        when(paymentGateway.charge(any(), any()))
                .thenReturn(PaymentResult.approved("TX-888"));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        service.placeOrder(order);

        InOrder sequence = inOrder(paymentGateway, repository, notificationService);
        sequence.verify(paymentGateway).charge("ORD-103", new BigDecimal("80.00"));
        sequence.verify(repository).save(order);
        sequence.verify(notificationService)
                .sendOrderConfirmation("ola@example.com", "ORD-103");
    }

    private static Order order(String id, String email, String total) {
        return new Order(id, email, new BigDecimal(total));
    }
}

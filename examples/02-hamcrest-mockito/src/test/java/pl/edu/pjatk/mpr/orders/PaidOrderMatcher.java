package pl.edu.pjatk.mpr.orders;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class PaidOrderMatcher extends TypeSafeDiagnosingMatcher<Order> {
    private final String expectedId;

    private PaidOrderMatcher(String expectedId) {
        this.expectedId = expectedId;
    }

    public static PaidOrderMatcher paidOrderWithId(String id) {
        return new PaidOrderMatcher(id);
    }

    @Override
    protected boolean matchesSafely(Order order, Description mismatch) {
        if (!expectedId.equals(order.getId())) {
            mismatch.appendText("id was ").appendValue(order.getId());
            return false;
        }
        if (order.getStatus() != OrderStatus.PAID) {
            mismatch.appendText("status was ").appendValue(order.getStatus());
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a paid order with id ").appendValue(expectedId);
    }
}

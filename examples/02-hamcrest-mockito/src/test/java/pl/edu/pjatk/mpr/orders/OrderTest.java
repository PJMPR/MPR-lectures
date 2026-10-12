package pl.edu.pjatk.mpr.orders;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

class OrderTest {

    @Test
    void newOrder_hasExpectedProperties() {
        Order order = order("ORD-101", "ada@example.com", "149.90");

        assertThat(order, allOf(
                hasProperty("id", is("ORD-101")),
                hasProperty("customerEmail", is("ada@example.com")),
                hasProperty("total", comparesEqualTo(new BigDecimal("149.90"))),
                hasProperty("status", is(OrderStatus.NEW))
        ));
    }

    @Test
    void matchers_describeCollectionsPrecisely() {
        List<String> ids = List.of("ORD-101", "ORD-102", "ORD-103");

        assertThat(ids, hasSize(3));
        assertThat(ids, everyItem(startsWith("ORD-")));
        assertThat(ids, contains("ORD-101", "ORD-102", "ORD-103"));
        assertThat(ids, containsInAnyOrder("ORD-103", "ORD-101", "ORD-102"));
        assertThat(ids, is(not(empty())));
    }

    private static Order order(String id, String email, String total) {
        return new Order(id, email, new BigDecimal(total));
    }
}

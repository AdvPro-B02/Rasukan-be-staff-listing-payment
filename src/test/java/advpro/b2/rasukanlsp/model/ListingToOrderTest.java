package advpro.b2.rasukanlsp.model;

import advpro.b2.rasukanlsp.model.builder.ListingToOrderBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListingToOrderTest {

    private ListingToOrderBuilder listingToOrderBuilder;

    @BeforeEach
    void setUp() {
        UUID listingId = UUID.randomUUID();
        Order order = new Order();
        int quantity = 5;
        listingToOrderBuilder = new ListingToOrderBuilder(listingId, order, quantity);
    }

    @Test
    void testCreateListingToOrder_success() {
        UUID listingToOrderId = UUID.randomUUID();
        UUID listingId = UUID.randomUUID();
        Order order = new Order();
        int quantity = 5;

        listingToOrderBuilder.setListingToOrderId(listingToOrderId);
        listingToOrderBuilder.setListing(listingId);
        listingToOrderBuilder.setOrder(order);
        listingToOrderBuilder.setQuantity(quantity);

        ListingToOrder listingToOrder = listingToOrderBuilder.build();

        assertEquals(listingToOrderId, listingToOrder.getListingInOrderId());
        assertEquals(listingId, listingToOrder.getListingId());
        assertEquals(order, listingToOrder.getOrder());
        assertEquals(quantity, listingToOrder.getQuantity());
    }

    @Test
    void testSetOrder() {
        Order order = new Order();
        ListingToOrder listingToOrder = new ListingToOrder();

        listingToOrder.setOrder(order);

        assertEquals(order, listingToOrder.getOrder());
    }

    @Test
    void testSetQuantity() {
        int quantity = 10;
        ListingToOrder listingToOrder = new ListingToOrder();

        listingToOrder.setQuantity(quantity);

        assertEquals(quantity, listingToOrder.getQuantity());
    }
}
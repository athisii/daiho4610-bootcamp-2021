package demo.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(5, "Apple", 100.0);
    }


    @Test
    void getQuantity() {
        int quantity = order.getQuantity();
        assertEquals(5, quantity);
    }

    @Test
    void setQuantity() {
        Order returnObject = order.setQuantity(5);
        assertEquals(order, returnObject);
    }

    @Test
    void getItemName() {
        String itemName = order.getItemName();
        assertEquals("Apple", itemName);

    }

    @Test
    void setItemName() {
        Order returnObject = order.setItemName("Apple");
        assertEquals(order, returnObject);
    }

    @Test
    void getPrice() {
        double price = order.getPrice();
        assertEquals(100.0, price);

    }

    @Test
    void setPrice() {
        Order returnObject = order.setPrice(100.0);
        assertEquals(order, returnObject);
    }

    @Test
    void getPriceWithTex() {
        order.setPriceWithTex(120.0);
        double priceWithTex = order.getPriceWithTex();
        assertEquals(120.0, priceWithTex);

    }

    @Test
    void setPriceWithTex() {
        Order returnObject = order.setPriceWithTex(120.0);
        assertEquals(order, returnObject);
    }

}
package com.tothenew.entities.user;

import com.tothenew.entities.cart.Cart;
import com.tothenew.entities.order.Order;
import com.tothenew.entities.product.ProductReview;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private String contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ProductReview> productReview = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

    public String getContact() {
        return contact;
    }

    public Customer setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Customer setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public List<ProductReview> getProductReview() {
        return productReview;
    }

    public Customer setProductReview(List<ProductReview> productReview) {
        this.productReview = productReview;
        return this;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public Customer setCarts(List<Cart> carts) {
        this.carts = carts;
        return this;
    }
}
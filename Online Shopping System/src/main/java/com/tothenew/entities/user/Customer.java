package com.tothenew.entities.user;

import com.tothenew.entities.order.Order;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    private String contact;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    public String getContact() {
        return contact;
    }

    public Customer setContact(String contact) {
        this.contact = contact;
        return this;
    }

}
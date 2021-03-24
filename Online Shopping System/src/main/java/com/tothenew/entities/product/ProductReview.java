package com.tothenew.entities.product;

import com.tothenew.entities.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {

    @Id
    private int id;
    private String review;
    private int rating;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}

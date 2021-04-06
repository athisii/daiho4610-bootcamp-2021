package com.tothenew.entities.product;

import com.tothenew.entities.user.Customer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    @EmbeddedId
    private ProductReviewKey productReviewKey = new ProductReviewKey();
    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private String review;
    private int rating;


}

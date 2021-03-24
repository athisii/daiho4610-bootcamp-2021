package com.tothenew.entities.cart;

import com.tothenew.entities.product.ProductVariation;
import com.tothenew.entities.user.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @EmbeddedId
    private CustomerProductVariationKey customerProductVariationKey = new CustomerProductVariationKey();
    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productVariationId")
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    private int quantity;
    private boolean isWishlistItem;

}

package com.tothenew.entities.cart;

import com.tothenew.entities.product.ProductVariation;
import com.tothenew.entities.user.Customer;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

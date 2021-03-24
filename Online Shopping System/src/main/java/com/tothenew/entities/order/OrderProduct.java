package com.tothenew.entities.order;

import com.tothenew.entities.product.ProductVariation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productVariationMetadata;
    private int quantity;
    private int price;

    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;

}

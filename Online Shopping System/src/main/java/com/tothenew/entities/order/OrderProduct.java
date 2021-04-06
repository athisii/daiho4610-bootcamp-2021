package com.tothenew.entities.order;

import com.tothenew.entities.product.ProductVariation;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productVariationMetadata;
    private int quantity;
    private int price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(optional = false)
    @JoinColumn(name = "product_id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct")
    private OrderStatus orderStatus;

}

package com.tothenew.entities.order;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.tothenew.entities.product.ProductVariation;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
@JsonFilter("orderProductFilter")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String productVariationMetadata;
    private int quantity;
    private int price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "orderProduct", cascade = CascadeType.ALL)
    private OrderStatus orderStatus;

}

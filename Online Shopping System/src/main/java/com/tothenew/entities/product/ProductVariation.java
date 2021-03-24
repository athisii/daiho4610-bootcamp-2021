package com.tothenew.entities.product;

import com.tothenew.entities.order.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
//@EqualsAndHashCode(exclude = {"product", "orderProduct"})
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantityAvailable;
    private int price;
    //PRIMARY_IMAGE_NAME
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToOne(mappedBy = "productVariation")
    private OrderProduct orderProduct;

}

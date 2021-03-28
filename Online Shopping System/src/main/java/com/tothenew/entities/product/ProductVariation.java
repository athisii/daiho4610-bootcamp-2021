package com.tothenew.entities.product;

import com.tothenew.entities.cart.Cart;
import com.tothenew.entities.order.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"product", "orderProduct"})
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantityAvailable;
    private int price;
    private String primaryImageName;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @OneToOne(mappedBy = "productVariation")
    private OrderProduct orderProduct;

    //Metadata(Json)
    @OneToOne
    @JoinColumn(name = "metadata")
    private Category category;

    @OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
    private List<Cart> carts = new ArrayList<>();

}

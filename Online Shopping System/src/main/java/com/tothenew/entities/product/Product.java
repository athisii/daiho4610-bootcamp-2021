package com.tothenew.entities.product;

import com.tothenew.entities.user.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
//@EqualsAndHashCode(exclude = {"seller"})
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private Boolean isCancellable;
    private String brand;
    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductVariation productVariation;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductReview productReview;

}

package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tothenew.entities.user.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
//@EqualsAndHashCode(exclude = {"seller"})
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"productVariations", "productReview"})
@JsonFilter("productFilter")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String description;
    private boolean isCancelable;
    private boolean isReturnable;
    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariation> productVariations;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductReview> productReview;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", isCancelable=" + isCancelable +
                ", isReturnable=" + isReturnable +
                ", seller=" + seller.getEmail() +
                ", category=" + category.getName() +
                '}';
    }
}

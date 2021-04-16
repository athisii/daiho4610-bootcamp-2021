package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.tothenew.entities.user.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("productFilter")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String description;
    private boolean isCancelable;
    private boolean isReturnable;
    private boolean isActive = true;
    private boolean isDeleted;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductVariation> productVariations;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<ProductReview> productReview;

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

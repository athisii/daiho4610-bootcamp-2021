package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
@JsonFilter("productVariationFilter")
@EntityListeners(AuditingEntityListener.class)
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantityAvailable;
    private int price;
    private String primaryImageName;
    private boolean isActive = true;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    //Metadata(Json)
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private String metadata;


//    @OneToOne
//    @JoinColumn(name = "metadata")
//    private Category category;

//    @OneToMany(mappedBy = "productVariation", cascade = CascadeType.ALL)
//    private List<Cart> carts = new ArrayList<>();
//
//    @OneToOne(mappedBy = "productVariation")
//    private OrderProduct orderProduct;

}

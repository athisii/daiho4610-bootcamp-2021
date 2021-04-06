package com.tothenew.entities.product;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(
        name = "json",
        typeClass = JsonStringType.class
)
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantityAvailable;
    private int price;
    private String primaryImageName;
    private boolean isActive = true;

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

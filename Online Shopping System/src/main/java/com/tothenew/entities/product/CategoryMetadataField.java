package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class CategoryMetadataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @JsonIgnore
//    @OneToMany(mappedBy = "categoryMetadataField", cascade = CascadeType.ALL)
//    private List<CategoryMetadataFieldValues> categoryMetadataFieldValues = new ArrayList<>();

    public CategoryMetadataField(String name) {
        this.name = name;
    }


}

package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
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

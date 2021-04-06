package com.tothenew.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class CategoryMetadataFieldValues {
    @JsonIgnore
    @EmbeddedId
    private CategoryMetadataFieldKey categoryMetadataFieldKey = new CategoryMetadataFieldKey();

    @ManyToOne
    @MapsId("categoryMetadataFieldId")
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

    //CSV
    private String value;
}

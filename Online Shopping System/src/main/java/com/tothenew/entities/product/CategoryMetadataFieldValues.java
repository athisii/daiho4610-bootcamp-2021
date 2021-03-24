package com.tothenew.entities.product;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CategoryMetadataFieldValues {
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

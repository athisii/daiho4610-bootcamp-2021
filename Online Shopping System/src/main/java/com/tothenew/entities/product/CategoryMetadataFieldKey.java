package com.tothenew.entities.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;


@Data
@NoArgsConstructor
public class CategoryMetadataFieldKey implements Serializable {

    private int categoryMetadataField;
    private int category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryMetadataFieldKey that = (CategoryMetadataFieldKey) o;
        return categoryMetadataField == that.categoryMetadataField && category == that.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryMetadataField, category);
    }
}

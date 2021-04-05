package com.tothenew.objects.product;

import lombok.Getter;

@Getter
public class ProductVariationMetadataFieldValueDto {
    private String metadataFieldId;
    private String value;

    @Override
    public String toString() {
        return "ProductVariationMetadataFieldValueDto{" +
                "metadataFieldId='" + metadataFieldId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

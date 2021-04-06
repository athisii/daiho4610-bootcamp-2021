package com.tothenew.objects.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductVariationMetadataFieldValueDto {
    @NotNull
    @NotEmpty
    private Long metadataFieldId;
    @NotNull
    @NotEmpty
    private String value;

    @Override
    public String toString() {
        return "ProductVariationMetadataFieldValueDto{" +
                "metadataFieldId='" + metadataFieldId + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

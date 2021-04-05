package com.tothenew.objects.product;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateProductVariationDto {
    private Long productId;
    private int quantityAvailable;
    private int price;
    private String primaryImageName;
    List<ProductVariationMetadataFieldValueDto> metadata;

    @Override
    public String toString() {
        return "CreateProductVariationDto{" +
                "productId=" + productId +
                ", quantityAvailable=" + quantityAvailable +
                ", price=" + price +
                ", primaryImageName='" + primaryImageName + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}

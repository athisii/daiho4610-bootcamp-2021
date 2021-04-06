package com.tothenew.objects.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CreateProductVariationDto {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantityAvailable;
    @NotNull
    private Integer price;
    @NotNull
    @NotEmpty
    private String primaryImageName;
    @NotNull
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

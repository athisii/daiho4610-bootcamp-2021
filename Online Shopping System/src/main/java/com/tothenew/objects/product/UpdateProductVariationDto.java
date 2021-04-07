package com.tothenew.objects.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UpdateProductVariationDto {
    @NotNull
    private Long productVariationId;
    private Integer quantityAvailable;
    private Integer price;
    private String primaryImageName;
    private List<String> secondaryImages;
    private boolean isActive;
    List<ProductVariationMetadataFieldValueDto> updateMetadata;
}

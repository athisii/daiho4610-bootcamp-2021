package com.tothenew.objects.categorymetadata;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;


@Getter
public class CategoryMetadataFieldValuesDto {
    @NotNull
    private Long categoryId;
    @NotNull
    private LinkedHashSet<MetadataFieldIdValue> metadataFieldIdValues;
}

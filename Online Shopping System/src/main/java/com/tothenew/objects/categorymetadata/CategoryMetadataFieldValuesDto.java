package com.tothenew.objects.categorymetadata;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;


@Getter
public class CategoryMetadataFieldValuesDto {
    @NotNull
    private Long categoryId;
    @NotNull
    private LinkedHashSet<MetadataFieldId> metadataFieldIds;
    @NotEmpty
    private LinkedHashSet<MetadataFieldValue> values;

    @Override
    public String toString() {
        return "CreateCategoryMetadataFieldValues{" +
                "categoryId=" + categoryId +
                ", metadataFiledIds=" + metadataFieldIds +
                ", values=" + values +
                '}';
    }
}

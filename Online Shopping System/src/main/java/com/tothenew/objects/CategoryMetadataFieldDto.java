package com.tothenew.objects;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CategoryMetadataFieldDto {
    @NotNull
    @NotEmpty
    private String name;
}

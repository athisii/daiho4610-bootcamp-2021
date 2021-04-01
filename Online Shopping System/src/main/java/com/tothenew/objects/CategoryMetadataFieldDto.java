package com.tothenew.objects;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CategoryMetadataFieldDto {
    @NotNull
    @NotEmpty
    private String name;
}

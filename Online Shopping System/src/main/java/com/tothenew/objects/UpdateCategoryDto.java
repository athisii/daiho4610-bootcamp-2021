package com.tothenew.objects;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class UpdateCategoryDto {
    @NotNull
    private Long id;
    @NotEmpty
    private String name;
}

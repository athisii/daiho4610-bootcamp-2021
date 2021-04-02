package com.tothenew.objects;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CreateCategoryDto {
    @NotEmpty
    private String name;
    private Long categoryParentId;
}

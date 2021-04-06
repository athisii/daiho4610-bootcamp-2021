package com.tothenew.objects;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateCategoryDto {
    @NotEmpty
    private String name;
    private Long categoryParentId;
}

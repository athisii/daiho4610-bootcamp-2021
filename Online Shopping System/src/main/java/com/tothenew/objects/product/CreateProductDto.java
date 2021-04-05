package com.tothenew.objects.product;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CreateProductDto {
    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String brand;
    @NotNull
    private Long category;
    private String description;
    private boolean isCancelable;
    private boolean isReturnable;
}

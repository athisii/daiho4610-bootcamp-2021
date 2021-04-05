package com.tothenew.objects.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateProductDto {
    @NotNull
    private Long productId;
    private String name;
    private String description;
    private boolean isCancelable;
    private boolean isReturnable;

    @Override
    public String toString() {
        return "UpdateProductDto{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isCancelable=" + isCancelable +
                ", isReturnable=" + isReturnable +
                '}';
    }
}

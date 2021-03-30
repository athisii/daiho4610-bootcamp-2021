package com.tothenew.objects;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AddressDto {
    @NotNull
    @Size(min = 1)
    private String city;
    @NotNull
    @Size(min = 1)
    private String state;
    @NotNull
    @Size(min = 1)
    private String country;
    @NotNull
    @Size(min = 1)
    private String addressLine;
    @NotNull
    @Size(min = 6)
    private String zipCode;
    @NotNull
    @Size(min = 4)
    private String label;
}

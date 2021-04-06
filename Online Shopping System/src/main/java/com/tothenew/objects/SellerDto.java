package com.tothenew.objects;

import com.tothenew.entities.user.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class SellerDto extends UserDto {
    private String GST;
    @NotEmpty
    @NotNull
    private String companyName;

    @NotNull
    private Address companyAddress;
}

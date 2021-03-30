package com.tothenew.objects;

import com.tothenew.entities.user.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class SellerDto extends UserDto {
    //    GST
    @NotEmpty
    @NotNull
    private String companyName;

    @NotNull
    private Address companyAddress;
}

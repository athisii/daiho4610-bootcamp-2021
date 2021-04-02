package com.tothenew.objects;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class UpdateProfileDto {

    @NotNull
    @Size(min = 1, message = "First Name should have at least 1 character")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "Last Name should have at least 1 character")
    private String lastName;

    private String middleName;

}

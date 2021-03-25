package com.tothenew.objects;

import com.tothenew.validation.PasswordMatches;
import com.tothenew.validation.ValidEmail;
import com.tothenew.validation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter

@PasswordMatches
public class UserDto {
    @NotNull
    @Size(min = 1, message = "First Name should have at least 1 character")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "Last Name should have at least 1 character")
    private String lastName;

    private String middleName;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "Email should be well-formed")
    private String email;

}

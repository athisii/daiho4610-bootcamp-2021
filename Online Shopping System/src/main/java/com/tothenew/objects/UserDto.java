package com.tothenew.objects;

import com.tothenew.entities.user.Address;
import com.tothenew.validation.password.PasswordMatches;
import com.tothenew.validation.password.ValidPassword;
import com.tothenew.validation.email.EmailValidator;
import com.tothenew.validation.phonenumber.Phone;
import com.tothenew.validation.phonenumber.PhoneNumber;
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

    @EmailValidator.ValidEmail
    @NotNull
    @Size(min = 1, message = "Email should be well-formed")
    private String email;

    @Phone
    private PhoneNumber phone;
    private Address address;

}

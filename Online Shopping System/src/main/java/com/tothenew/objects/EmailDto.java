package com.tothenew.objects;

import com.tothenew.validation.email.EmailValidator;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
public class EmailDto {

    @EmailValidator.ValidEmail
    @NotNull
    @Size(min = 1, message = "Email should be well-formed")
    private String email;

}

package com.tothenew.objects;

import com.tothenew.validation.email.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class EmailDto {

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "Email should be well-formed")
    private String email;

}

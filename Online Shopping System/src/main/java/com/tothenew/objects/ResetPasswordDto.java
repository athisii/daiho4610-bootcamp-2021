package com.tothenew.objects;

import com.tothenew.validation.password.ResetPasswordMatches;
import com.tothenew.validation.password.ValidPassword;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ResetPasswordMatches
public class ResetPasswordDto {
    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;
}

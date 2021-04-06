package com.tothenew.objects;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UsernameAndPasswordAuthenticationRequest {
    @Email
    private String email;
    private String password;
}

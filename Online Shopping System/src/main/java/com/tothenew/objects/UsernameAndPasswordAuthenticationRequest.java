package com.tothenew.objects;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UsernameAndPasswordAuthenticationRequest {
    @Email
    private String email;
    private String password;
}

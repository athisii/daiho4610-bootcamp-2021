package com.tothenew.jwt;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UsernameAndPasswordAuthenticationRequest {
    @Email
    private String email;
    private String password;
}

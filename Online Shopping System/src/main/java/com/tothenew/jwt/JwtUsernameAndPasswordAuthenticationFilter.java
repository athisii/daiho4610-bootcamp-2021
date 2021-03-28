package com.tothenew.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tothenew.exception.RequestBodyException;
import com.tothenew.exception.UserNotFoundException;
import com.tothenew.services.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtCofig jwtCofig;
    private final SecretKey secretKey;
    private final UserService userService;
    private String email;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtCofig jwtCofig, SecretKey secretKey, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtCofig = jwtCofig;
        this.secretKey = secretKey;
        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest emailAndPassword = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            email = emailAndPassword.getEmail();
            Authentication authentication = new UsernamePasswordAuthenticationToken(emailAndPassword.getEmail(), emailAndPassword.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException exception) {
            throw new RequestBodyException("Error reading the request body.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtCofig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtCofig.getAuthorizationHeader(), jwtCofig.getTokenPrefix() + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("Invalid Credentials");
        System.out.println("Email: " + email);
        userService.findUserByEmail(email);
        super.unsuccessfulAuthentication(request, response, failed);

    }
}

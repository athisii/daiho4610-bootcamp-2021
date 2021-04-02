package com.tothenew.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tothenew.entities.user.User;
import com.tothenew.exception.ExceptionResponse;
import com.tothenew.exception.RequestBodyException;
import com.tothenew.objects.UsernameAndPasswordAuthenticationRequest;
import com.tothenew.services.user.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
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

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtCofig jwtCofig,
                                                      SecretKey secretKey,
                                                      UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtCofig = jwtCofig;
        this.secretKey = secretKey;
        this.userService = userService;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
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
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String authEmail = authResult.getName();
        User user = userService.findUserByEmail(authEmail);
        if (user.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(authEmail);
        }

        String token = Jwts.builder()
                .setSubject(authEmail)
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtCofig.getTokenExpirationAfterDays())))
                .signWith(secretKey)
                .compact();

        response.addHeader(jwtCofig.getAuthorizationHeader(), jwtCofig.getTokenPrefix() + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ExceptionResponse response1 = new ExceptionResponse(new Date(), "Bad Credentials", "Invalid Password");

        User user = userService.findUserByEmail(email);

        if (user != null) {
            if (user.isActive() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < UserService.MAX_FAILED_ATTEMPTS - 1) {
                    userService.increaseFailedAttempts(user);
                } else {
                    userService.lock(user);
                    userService.sendLockedMessage(user.getEmail());
                    response1 = new ExceptionResponse(new Date(), "Bad Credentials", "Invalid Password. Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (userService.unlockWhenTimeExpired(user)) {
                    userService.sendUnLockedMessage(user.getEmail());
                }
                response1 = new ExceptionResponse(new Date(), "Account Locked", "Your account has been locked due to 3 failed attempts.");
            }

        }
        new ObjectMapper().writeValue(response.getWriter(), response1);

    }


}

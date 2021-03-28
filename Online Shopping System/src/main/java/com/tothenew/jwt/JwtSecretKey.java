package com.tothenew.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {
    private final JwtCofig jwtCofig;

    @Autowired
    public JwtSecretKey(JwtCofig jwtCofig) {
        this.jwtCofig = jwtCofig;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtCofig.getSecretKey().getBytes());
    }
}

package com.tothenew.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tothenew.exception.ExceptionResponse;
import com.tothenew.exception.InvalidTokenException;
import com.tothenew.services.LogoutTokenService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private LogoutTokenService logoutTokenService;
    private final SecretKey secretKey;

    public CustomLogoutHandler(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            String token = header.substring(7);
            if (logoutTokenService.isBlacklisted(token)) {
                throw new InvalidTokenException("Invalid Token");
            }
            try {
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
                logoutTokenService.saveLogoutToken(token);
                ExceptionResponse response1 = new ExceptionResponse(new Date(), "Logout", "You have been logout successfully.");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getWriter(), response1);
            } catch (JwtException ignored) {
                throw new InvalidTokenException("Invalid Token");
            }

        }
    }
}

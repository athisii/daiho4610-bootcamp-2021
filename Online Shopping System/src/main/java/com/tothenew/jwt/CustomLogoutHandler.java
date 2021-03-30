package com.tothenew.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tothenew.exception.ExceptionResponse;
import com.tothenew.services.LogoutTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private LogoutTokenService logoutTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null) {
            String token = header.substring(7);
            logoutTokenService.saveLogoutToken(token);
            ExceptionResponse response1 = new ExceptionResponse(new Date(), "Logout", "You have been logout successfully.");
            new ObjectMapper().writeValue(response.getWriter(), response1);
        }
    }
}

package com.tothenew.jwt;

import com.google.common.base.Strings;
import com.tothenew.exception.InvalidTokenException;
import com.tothenew.services.LogoutTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtCofig jwtCofig;
    private final SecretKey secretKey;
    private final LogoutTokenService logoutTokenService;

    @Autowired
    public JwtTokenVerifier(SecretKey secretKey, JwtCofig jwtCofig, LogoutTokenService logoutTokenService) {
        this.secretKey = secretKey;
        this.jwtCofig = jwtCofig;
        this.logoutTokenService = logoutTokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws InvalidTokenException, ServletException, IOException {

        String authorizationHeader = request.getHeader(jwtCofig.getAuthorizationHeader());
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtCofig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        if (!logoutTokenService.isBlacklisted(token)) {
            try {
//
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();
                String email = body.getSubject();
                var authorities = (List<Map<String, String>>) body.get("authorities");
                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                        .collect(Collectors.toSet());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, simpleGrantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException ignored) {
            }

        }
        filterChain.doFilter(request, response);
    }


}


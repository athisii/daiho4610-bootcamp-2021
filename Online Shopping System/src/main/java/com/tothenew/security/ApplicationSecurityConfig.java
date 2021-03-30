package com.tothenew.security;

import com.tothenew.jwt.JwtCofig;
import com.tothenew.jwt.JwtTokenVerifier;
import com.tothenew.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.tothenew.services.AppUserDetailsService;
import com.tothenew.services.LogoutTokenService;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtCofig jwtCofig;
    private final SecretKey secretKey;
    private final AppUserDetailsService appUserDetailsService;
    private final UserService userService;
    private final LogoutTokenService logoutTokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LogoutSuccessHandler logoutSuccessHandler;


    @Autowired
    public ApplicationSecurityConfig(JwtCofig jwtCofig,
                                     SecretKey secretKey,
                                     AppUserDetailsService appUserDetailsService,
                                     UserService userService,
                                     LogoutTokenService logoutTokenService) {
        this.jwtCofig = jwtCofig;
        this.secretKey = secretKey;
        this.appUserDetailsService = appUserDetailsService;
        this.userService = userService;
        this.logoutTokenService = logoutTokenService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtCofig, secretKey, userService))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtCofig, logoutTokenService), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/seller/**").hasAnyRole("SELLER")
                .antMatchers("/customer/**").hasAnyRole("CUSTOMER")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler);
    }


}

package com.tothenew.security;

import com.tothenew.jwt.JwtCofig;
import com.tothenew.jwt.JwtTokenVerifier;
import com.tothenew.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.tothenew.services.AppUserDetailsService;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;


@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtCofig jwtCofig;
    private final SecretKey secretKey;
    private final AppUserDetailsService appUserDetailsService;
    private final UserService userService;

    @Autowired
    public ApplicationSecurityConfig(JwtCofig jwtCofig, SecretKey secretKey, AppUserDetailsService appUserDetailsService, UserService userService) {
        this.jwtCofig = jwtCofig;
        this.secretKey = secretKey;
        this.appUserDetailsService = appUserDetailsService;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(appUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtCofig, secretKey, userService))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtCofig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/admin/home").hasAnyRole("ADMIN")
                .antMatchers("/user/home").hasAnyRole("CUSTOMER")
                .antMatchers("/").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

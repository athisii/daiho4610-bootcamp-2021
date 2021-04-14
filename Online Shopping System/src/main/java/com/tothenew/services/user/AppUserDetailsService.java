package com.tothenew.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserDaoService userDaoService;

    @Autowired
    public AppUserDetailsService(@Qualifier("AppUser") AppUserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDaoService.loadUserByUsername(email);
    }
}
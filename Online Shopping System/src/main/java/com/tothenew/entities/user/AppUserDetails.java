package com.tothenew.entities.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class AppUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonLocked;
    Set<SimpleGrantedAuthority> grantAuthorities;

    public AppUserDetails(String username, String password, boolean isEnabled, boolean isAccountNonLocked, Set<SimpleGrantedAuthority> grantAuthorities) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.isAccountNonLocked = isAccountNonLocked;
        this.grantAuthorities = grantAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
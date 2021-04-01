package com.tothenew.services;

import com.tothenew.entities.user.AppUserDetails;
import com.tothenew.entities.user.User;
import com.tothenew.repos.user.AppUserDao;
import com.tothenew.repos.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository("AppUser")
public class AppUserDaoService implements AppUserDao {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserDaoService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AppUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Set<SimpleGrantedAuthority> grantAuthorityList = new HashSet<>();
            user.getRoles().forEach(role -> grantAuthorityList.add(new SimpleGrantedAuthority(role.getAuthority())));
            return new AppUserDetails(user.getEmail(), user.getPassword(), user.isActive(), user.isAccountNonLocked(), grantAuthorityList);
        } else {
            throw new UsernameNotFoundException("No such user found");
        }

    }


}

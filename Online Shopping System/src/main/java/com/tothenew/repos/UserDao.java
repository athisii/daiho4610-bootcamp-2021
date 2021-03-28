package com.tothenew.repos;

import com.tothenew.entities.user.AppUser;
import com.tothenew.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;


    @Transactional
    public AppUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Set<SimpleGrantedAuthority> grantAuthorityList = new HashSet<>();
            user.getRoles().forEach(role -> grantAuthorityList.add(new SimpleGrantedAuthority(role.getAuthority())));
            return new AppUser(user.getEmail(), user.getPassword(), user.isActive(), user.isAccountNonLocked(), grantAuthorityList);
        } else {
            throw new UsernameNotFoundException("No such user found");
        }

    }
}

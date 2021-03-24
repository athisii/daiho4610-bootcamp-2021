package com.tothenew.repos;

import com.tothenew.security.GrantAuthorityImpl;
import com.tothenew.entities.user.AppUser;
import com.tothenew.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    UserRepository userRepository;


    @Transactional
    public AppUser loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println("Hello World");
        if (email != null) {
            List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
            user.getRoles().forEach(role -> grantAuthorityList.add(new GrantAuthorityImpl(role.getAuthority())));
            return new AppUser(user.getEmail(), user.getPassword(), grantAuthorityList);
        } else {
            throw new RuntimeException();
        }

    }
}

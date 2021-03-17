package com.tothenew.repos;

import com.tothenew.auth.GrantAuthorityImpl;
import com.tothenew.entities.AppUser;
import com.tothenew.entities.User;
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
    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (username != null) {
            List<GrantAuthorityImpl> grantAuthorityList = new ArrayList<>();
            user.getRoles().forEach(role -> grantAuthorityList.add(new GrantAuthorityImpl(role.getName())));
            return new AppUser(user.getUsername(), user.getPassword(), grantAuthorityList);
        } else {
            throw new RuntimeException();
        }

    }
}

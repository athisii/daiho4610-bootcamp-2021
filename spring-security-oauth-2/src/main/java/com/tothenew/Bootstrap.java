package com.tothenew;


import com.tothenew.entities.Role;
import com.tothenew.entities.User;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() < 1) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user1 = new User();
            user1.setUsername("user");
            user1.setPassword(passwordEncoder.encode("pass"));

            Role role1 = new Role();
            role1.setName("ROLE_USER");
            user1.addRole(role1);

            User user2 = new User();
            user2.setUsername("admin");
            user2.setPassword(passwordEncoder.encode("pass"));

            Role role2 = new Role();
            role2.setName("ROLE_ADMIN");
            user2.addRole(role2);

            userRepository.save(user1);
            userRepository.save(user2);
            System.out.println("Total users saved::" + userRepository.count());

        }
    }
}

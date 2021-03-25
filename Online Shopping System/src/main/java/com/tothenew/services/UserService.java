package com.tothenew.services;

import com.tothenew.entities.user.*;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public List<Seller> getAllSellers() {
        return userRepository.findAllSellers();
    }


}

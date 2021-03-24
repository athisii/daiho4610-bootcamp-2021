package com.tothenew.services;

import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Seller;
import com.tothenew.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public List<Seller> getAllSellers() {
        return userRepository.findAllSellers();
    }
}

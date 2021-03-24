package com.tothenew.resources;

import com.tothenew.entities.user.Customer;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/customer")
@RestController
public class CustomerResource {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Customer> retrieveAllCustomers() {
        return userService.getAllCustomers();
    }

}

package com.tothenew.resources;


import com.tothenew.entities.user.Customer;
import com.tothenew.entities.user.Seller;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/admin")
@RestController
public class AdminResource {

    @Autowired
    private UserService userService;

    @GetMapping("/customer")
    public List<Customer> retrieveAllCustomers() {
        return userService.getAllCustomers();
    }

    @GetMapping("/seller")
    public List<Seller> retrieveAllSellers() {
        return userService.getAllSellers();
    }

    @PutMapping("/activate-user/{userId}")
    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
        userService.activateUserById(userId);
        return new ResponseEntity<>("User account activated successfully!", HttpStatus.OK);
    }

    @PutMapping("/deactivate-user/{userId}")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
        userService.deactivateUserById(userId);
        return new ResponseEntity<>("User account successfully deactivated!", HttpStatus.OK);
    }


}

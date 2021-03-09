package com.ttn.restfulwebservices.content_negotiation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/content-negotiation")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> retrieveAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }


}

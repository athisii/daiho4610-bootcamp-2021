package com.tothenew.resources;

import com.tothenew.entities.user.User;
import com.tothenew.objects.ResetPasswordDto;
import com.tothenew.repos.UserRepository;
import com.tothenew.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/")
@RestController
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/admin/home")
    public String adminHome() {
        return "Admin home";
    }

    @GetMapping("/user/home")
    public String customerHome() {
        return "Customer home";
    }



}

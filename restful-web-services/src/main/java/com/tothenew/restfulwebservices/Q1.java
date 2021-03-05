package com.tothenew.restfulwebservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class Q1 {

    @GetMapping
    public String welcome() {
        return "Welcome to spring boot";
    }
}

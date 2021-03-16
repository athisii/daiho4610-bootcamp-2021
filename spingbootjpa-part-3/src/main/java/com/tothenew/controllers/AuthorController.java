package com.tothenew.controllers;

import com.tothenew.entities.Author;
import com.tothenew.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/")
@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @GetMapping
    public List<Author> retrieveAuthor() {
        return authorService.getAllAuthors();

    }

}

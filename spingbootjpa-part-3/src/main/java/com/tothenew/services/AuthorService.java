package com.tothenew.services;

import com.tothenew.entities.Author;
import com.tothenew.repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;


    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


}

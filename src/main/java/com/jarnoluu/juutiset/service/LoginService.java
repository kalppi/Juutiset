package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Author;
import com.jarnoluu.juutiset.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthorRepository authorRepository;
    
    public Author getLoggedInAuthor() {
        return this.authorRepository.findAll().get(1);
    }
}

package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Author;
import com.jarnoluu.juutiset.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private AuthorRepository authorRepository;
    
    public Author getLoggedInAuthor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth == null) return null;
        
        User user = (User)auth.getPrincipal();
        Author author = this.authorRepository.findByName(user.getUsername());
        
        return author;
    }
}

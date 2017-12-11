package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Author;
import com.jarnoluu.juutiset.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JuutisetUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthorRepository authorRepository;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = this.authorRepository.findByUsername(username);
        
        if (author == null) {
            throw new UsernameNotFoundException("Käyttäjää ei löydy: "+ username);
        }
        
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return  new org.springframework.security.core.userdetails.User(
          author.getName(), 
          author.getPassword(), enabled, accountNonExpired, 
          credentialsNonExpired, accountNonLocked, 
          getAuthorities(Arrays.asList("ROLE_USER")));
    }
     
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
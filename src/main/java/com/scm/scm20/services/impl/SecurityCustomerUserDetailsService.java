package com.scm.scm20.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.scm20.repositories.UserRepo;

@Service
public class SecurityCustomerUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ko load karna hai
        return userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
    }

}
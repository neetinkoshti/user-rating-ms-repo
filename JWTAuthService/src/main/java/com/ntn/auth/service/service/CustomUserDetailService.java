package com.ntn.auth.service.service;

import com.ntn.auth.service.entity.UserCredential;
import com.ntn.auth.service.exceptions.BadCredentialsException;
import com.ntn.auth.service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {

        UserCredential credential = userCredentialRepository.findByUserName(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        return new CustomUserDetails(credential);
    }
}

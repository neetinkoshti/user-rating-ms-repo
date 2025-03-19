package com.ntn.auth.service.service;

import com.ntn.auth.service.entity.UserCredential;
import com.ntn.auth.service.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserCredential addUser(UserCredential userCredential) {

        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));

       UserCredential credential = userCredentialRepository.save(userCredential);
        return credential;
    }

    public String generateToken(String userName){
        return jwtService.generateToken(userName);
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}

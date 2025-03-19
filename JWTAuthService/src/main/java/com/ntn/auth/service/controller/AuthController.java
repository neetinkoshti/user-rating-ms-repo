package com.ntn.auth.service.controller;

import com.ntn.auth.service.entity.UserCredential;
import com.ntn.auth.service.exceptions.BadCredentialsException;
import com.ntn.auth.service.model.AuthRequest;
import com.ntn.auth.service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/test")
    public String test(){
        return "Auth Service is up and running";
    }

    @PostMapping("/register")
    public ResponseEntity<UserCredential> addUser(@RequestBody  UserCredential userCredential) {
        return new ResponseEntity<>(authService.addUser(userCredential) , HttpStatus.CREATED);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {

        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                return authService.generateToken(authRequest.getUserName());
            }
        }catch(RuntimeException e) {
                throw new BadCredentialsException("Invalid username or password!!");
        }
        return null;
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<String> validateToken(@PathVariable("token") String token) {
        authService.validateToken(token);
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }

}

package com.ntn.user.service.userservice.controller;

import com.ntn.user.service.userservice.model.User;
import com.ntn.user.service.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private static int  RETRY_COUNT = 0;

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String test() {
        return "User Service is up and running";
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers() , HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingCB", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userService.getUserUsingFeign(userId) , HttpStatus.OK);
    }

    @GetMapping("/retry/{userId}")
    @Retry(name = "ratingHotelRetry" , fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserRetry(@PathVariable("userId") String userId) {

        RETRY_COUNT++;
        logger.info("Retry count : {}",RETRY_COUNT);

        return new ResponseEntity<>(userService.getUserUsingFeign(userId) , HttpStatus.OK);
    }

    public ResponseEntity<User> ratingHotelFallback(String userId, RuntimeException e) {

        logger.info("ratingHotelFallback method called {} :",e.getMessage());

        User dummyUser = User.builder()
                .userName("dummyName")
                .email("dummyEmail")
                .about("dummyAbout")
                .userId("dummyUserId")
                .build();

        return new ResponseEntity<>(dummyUser , HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user) , HttpStatus.CREATED);
    }

    @PostMapping("/deleteUser/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user) , HttpStatus.OK);
    }



}

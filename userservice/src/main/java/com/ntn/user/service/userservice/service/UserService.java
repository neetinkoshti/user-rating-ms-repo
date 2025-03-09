package com.ntn.user.service.userservice.service;

import com.ntn.user.service.userservice.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUser(String userId);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(String userId);

    List<User> getAllUsers();
}

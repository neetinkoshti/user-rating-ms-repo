package com.ntn.user.service.userservice.service;

import com.ntn.user.service.userservice.entity.UserEntity;
import com.ntn.user.service.userservice.exceptions.ResourceNotFoundException;
import com.ntn.user.service.userservice.model.User;
import com.ntn.user.service.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        return userEntity.map(this::convertToModel)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server with given Id : "+userId));
    }

    @Override
    public User createUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        UserEntity entity = convertToEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return convertToModel(savedEntity);
    }

    @Override
    public User updateUser(User user) {
        if(userRepository.existsById(user.getUserId())) {
            UserEntity entity = convertToEntity(user);
            UserEntity savedEntity = userRepository.save(entity);
            return convertToModel(savedEntity);
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {

        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server with given Id : "+userId)));

    }

    @Override
    public List<User> getAllUsers(){

        List<UserEntity> entities = userRepository.findAll();

        List<User> list = entities.stream()
                .map(this::convertToModel)
                .toList();

        return list;
    }



    public UserEntity convertToEntity(User user) {

        return UserMapper.INSTANCE.userToUserEntity(user);
    }

    public User convertToModel(UserEntity userEntity) {

        return UserMapper.INSTANCE.userEntityToUser(userEntity);
    }
}

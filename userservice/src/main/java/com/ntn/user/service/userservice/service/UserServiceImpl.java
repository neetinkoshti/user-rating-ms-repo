package com.ntn.user.service.userservice.service;

import com.ntn.user.service.userservice.entity.RatingEntity;
import com.ntn.user.service.userservice.entity.UserEntity;
import com.ntn.user.service.userservice.exceptions.ResourceNotFoundException;
import com.ntn.user.service.userservice.model.User;
import com.ntn.user.service.userservice.model.Rating;
import com.ntn.user.service.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserServiceImpl  implements UserService{

    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User getUser(String userId) {

        // fetch user from repository
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        // fetch rating from Rating-service
        // http://rating-service/api/ratings/ratingByUserId/
        // localhost:8083/api/ratings/ratingByUserId/8dfc298e-1e38-460f-81c0-3a6f8f35b6bb
//        ArrayList<Rating> userRating = restTemplate.getForObject("http://localhost:8083/api/ratings/ratingByUserId/" + userId, ArrayList.class);

        ResponseEntity<List<Rating>> response = restTemplate.exchange(
                "http://localhost:8083/api/ratings/ratingByUserId/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<Rating> userRating = response.getBody();

        logger.info("Ratings fetched from rating-service : "+userRating);

        userEntity.ifPresent(entity -> {
            entity.setRatings(userRating.stream().map(this::userRatingToRatingEntity).toList());
        });

        return userEntity.map(this::userEntityToUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server with given Id : "+userId));
    }

    @Override
    public User createUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        UserEntity entity = userToUserEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return userEntityToUser(savedEntity);
    }

    @Override
    public User updateUser(User user) {
        if(userRepository.existsById(user.getUserId())) {
            UserEntity entity = userToUserEntity(user);
            UserEntity savedEntity = userRepository.save(entity);
            return userEntityToUser(savedEntity);
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
                .map(this::userEntityToUser)
                .toList();

        return list;
    }



    public UserEntity userToUserEntity(User user) {

        return UserMapper.INSTANCE.userToUserEntity(user);
    }

    public User userEntityToUser(UserEntity userEntity) {

        return UserMapper.INSTANCE.userEntityToUser(userEntity);
    }

    public RatingEntity userRatingToRatingEntity(Rating rating) {
        return UserMapper.INSTANCE.userRatingToRatingEntity(rating);
    }

    public Rating RatingEntityToUserRating(RatingEntity ratingEntity) {
        return UserMapper.INSTANCE.RatingEntityToUserRating(ratingEntity);
    }
}

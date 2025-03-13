package com.ntn.user.service.userservice.service;

import com.ntn.user.service.userservice.entity.UserEntity;
import com.ntn.user.service.userservice.exceptions.ResourceNotFoundException;
import com.ntn.user.service.userservice.model.Hotel;
import com.ntn.user.service.userservice.model.User;
import com.ntn.user.service.userservice.model.Rating;
import com.ntn.user.service.userservice.openfeign.service.HotelFeignService;
import com.ntn.user.service.userservice.openfeign.service.RatingFeignService;
import com.ntn.user.service.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    HotelFeignService hotelFeignService;

    @Autowired
    RatingFeignService  ratingFeignService;

    @Override
    public User getUser(String userId) {

        // fetch user from repository
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        User user = null;
        if(userEntity.isPresent()) {
            user = userEntityToUser(userEntity.get());


            // fetch rating from Rating-service
            // http://rating-service/api/ratings/ratingByUserId/
            // localhost:8083/api/ratings/ratingByUserId/8dfc298e-1e38-460f-81c0-3a6f8f35b6bb
//        ArrayList<Rating> userRating = restTemplate.getForObject("http://localhost:8083/api/ratings/ratingByUserId/" + userId, ArrayList.class);

            ResponseEntity<List<Rating>> response = restTemplate.exchange(
                    "http://RATING-SERVICE/api/ratings/ratingByUserId/" + user.getUserId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            List<Rating> userRating = response.getBody();

            // fetch hotel-service to get hotel details based on hotel id return by rating-service
            // localhost:8082/api/hotels/bb78e3f8-66d7-45e6-8cbb-322608756f24

            userRating.stream().forEach(rating -> {
                ResponseEntity<Hotel> responseEntity = restTemplate.exchange(
                        "http://HOTEL-SERVICE/api/hotels/" + rating.getHotelId(),
                        HttpMethod.GET,
                        null,
                        Hotel.class);
                Hotel hotel = responseEntity.getBody();
                rating.setHotel(hotel);
            });

            logger.info("Ratings fetched from rating-service with hotels : " + userRating);

            user.setRatings(userRating);
        }
        return user;
    }

    @Override
    public User getUserUsingFeign(String userId) {

        // fetch user from repository
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        User user = null;
        if(userEntity.isPresent()) {
            user = userEntityToUser(userEntity.get());

            // fetch rating from Rating-service using feign client
            List<Rating> userRating = ratingFeignService.getRating(user.getUserId());

            // Fetch hotel from HOTEL-SERVICE using feign client
            userRating.stream().forEach(rating -> {

                Hotel hotel = hotelFeignService.getHotel(rating.getHotelId());

                rating.setHotel(hotel);
            });
            user.setRatings(userRating);
        }

        return user;
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

 /*   public RatingEntity userRatingToRatingEntity(Rating rating) {
        return UserMapper.INSTANCE.userRatingToRatingEntity(rating);
    }

    public Rating RatingEntityToUserRating(RatingEntity ratingEntity) {
        return UserMapper.INSTANCE.RatingEntityToUserRating(ratingEntity);
    }*/
}

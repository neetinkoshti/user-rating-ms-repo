package com.ntn.user.service.userservice.service;

import com.ntn.user.service.userservice.entity.RatingEntity;
import com.ntn.user.service.userservice.entity.UserEntity;
import com.ntn.user.service.userservice.model.Rating;
import com.ntn.user.service.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userToUserEntity(User user);
    User userEntityToUser(UserEntity userEntity);

    RatingEntity userRatingToRatingEntity(Rating rating);
    Rating RatingEntityToUserRating(RatingEntity  ratingEntity);

}

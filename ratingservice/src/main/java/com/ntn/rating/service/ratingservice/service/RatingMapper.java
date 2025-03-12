package com.ntn.rating.service.ratingservice.service;

import com.ntn.rating.service.ratingservice.entity.RatingEntity;
import com.ntn.rating.service.ratingservice.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    RatingEntity ratingToRatingEntity(Rating rating);
    Rating ratingEntityToRating(RatingEntity ratingEntity);
}

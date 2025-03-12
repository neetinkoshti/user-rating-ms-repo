package com.ntn.rating.service.ratingservice.service;

import com.ntn.rating.service.ratingservice.entity.RatingEntity;
import com.ntn.rating.service.ratingservice.model.Rating;
import com.ntn.rating.service.ratingservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating addRating(Rating rating) {
        RatingEntity entity = ratingToRatingEntity(rating);
        return ratingEntityToRating(ratingRepository.save(entity));
    }

    public List<Rating> getAllRatings() {

        List<RatingEntity> ratingEntities = ratingRepository.findAll();
        return getRatingsByRatingEntity(ratingEntities);
    }

    private List<Rating> getRatingsByRatingEntity(List<RatingEntity> ratings) {
        if (!ratings.isEmpty()) {
            return ratings.stream().map(this::ratingEntityToRating).toList();
        }
        return new ArrayList<>();
    }

    public List<Rating> getRatingsByHotelId(String hotelId) {

        List<RatingEntity> ratingEntities = ratingRepository.findByHotelId(hotelId);
        return getRatingsByRatingEntity(ratingEntities);
    }

    public List<Rating> getRatingsByUserId(String userId) {
        List<RatingEntity> ratingEntities =ratingRepository.findByUserId(userId);
        return getRatingsByRatingEntity(ratingEntities);
    }

    public RatingEntity ratingToRatingEntity(Rating rating) {
        return RatingMapper.INSTANCE.ratingToRatingEntity(rating);
    }

    public Rating ratingEntityToRating(RatingEntity ratingEntity) {
        return RatingMapper.INSTANCE.ratingEntityToRating(ratingEntity);
    }
}

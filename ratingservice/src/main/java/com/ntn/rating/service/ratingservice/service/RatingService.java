package com.ntn.rating.service.ratingservice.service;

import com.ntn.rating.service.ratingservice.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    public Rating addRating(Rating rating);

    public List<Rating> getAllRatings();

    public List<Rating> getRatingsByHotelId(String hotelId);

    public List<Rating> getRatingsByUserId(String userId);
}

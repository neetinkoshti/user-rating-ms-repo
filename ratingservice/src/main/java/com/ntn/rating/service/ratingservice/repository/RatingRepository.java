package com.ntn.rating.service.ratingservice.repository;

import com.ntn.rating.service.ratingservice.entity.RatingEntity;
import com.ntn.rating.service.ratingservice.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<RatingEntity, String> {

    List<RatingEntity> findByUserId(String userId);

    List<RatingEntity> findByHotelId(String hotelId);
}

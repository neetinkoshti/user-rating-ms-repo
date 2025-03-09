package com.ntn.user.service.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RatingEntity {

    @Id
    private String ratingId;
    private String userId;
    private String hotelId;
    private int rating;
    private String feedback;

}

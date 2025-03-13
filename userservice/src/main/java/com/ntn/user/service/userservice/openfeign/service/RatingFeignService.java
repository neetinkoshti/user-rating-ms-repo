package com.ntn.user.service.userservice.openfeign.service;

import com.ntn.user.service.userservice.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingFeignService {

    @GetMapping("/api/ratings/ratingByUserId/{userId}")
    List<Rating> getRating(@PathVariable("userId") String userId);
}

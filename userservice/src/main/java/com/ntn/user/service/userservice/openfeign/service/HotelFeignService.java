package com.ntn.user.service.userservice.openfeign.service;

import com.ntn.user.service.userservice.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelFeignService {

        @GetMapping("/api/hotels/{hotelId}")
        Hotel getHotel(@PathVariable("hotelId")  String hotelId);
}

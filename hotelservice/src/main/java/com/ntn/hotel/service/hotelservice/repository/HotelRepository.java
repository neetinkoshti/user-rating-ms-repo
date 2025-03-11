package com.ntn.hotel.service.hotelservice.repository;

import com.ntn.hotel.service.hotelservice.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, String> {
}

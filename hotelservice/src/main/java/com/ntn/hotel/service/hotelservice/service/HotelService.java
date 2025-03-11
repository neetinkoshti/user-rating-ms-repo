package com.ntn.hotel.service.hotelservice.service;

import com.ntn.hotel.service.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {

    public Hotel addHotel(Hotel hotel);

    public Hotel updateHotel(String id, Hotel hotel);

    public void deleteHotel(String hotelId);

    public Hotel getHotel(String hotelId);

    public List<Hotel> getHotels();


}

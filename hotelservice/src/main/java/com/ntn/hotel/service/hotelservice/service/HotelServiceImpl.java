package com.ntn.hotel.service.hotelservice.service;

import com.ntn.hotel.service.hotelservice.entity.HotelEntity;
import com.ntn.hotel.service.hotelservice.exceptions.HotelNotFoundException;
import com.ntn.hotel.service.hotelservice.model.Hotel;
import com.ntn.hotel.service.hotelservice.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Override
    public Hotel addHotel(Hotel hotel) {
        hotel.setHotelId(UUID.randomUUID().toString());
        HotelEntity hotelEntity = convertToHotelEntity(hotel);
        return convertToHotel(hotelRepository.save(hotelEntity));
    }

    @Override
    public Hotel updateHotel(String id,Hotel hotel) {

        if(!hotelRepository.existsById(id)) {
            throw new HotelNotFoundException("Hotel not found on server with given Id : " + id);
        }else{
            HotelEntity entity = convertToHotelEntity(hotel);
            return convertToHotel(hotelRepository.save(entity));
        }
    }

    @Override
    public void deleteHotel(String hotelId) {
        hotelRepository.delete(hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found on server with given Id : " + hotelId)));
    }

    @Override
    public Hotel getHotel(String hotelId) {

        HotelEntity hotelEntity = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found on server with given Id : " + hotelId));
        return convertToHotel(hotelEntity);
    }

    @Override
    public List<Hotel> getHotels() {
        List<HotelEntity> hotelEntities = hotelRepository.findAll();
        return hotelEntities.stream()
                .map(this::convertToHotel)
                .toList();
    }

    public HotelEntity convertToHotelEntity(Hotel hotel) {
        return HotelMapper.INSTANCE.hotelToHotelEntity(hotel);
    }

    public Hotel convertToHotel(HotelEntity hotelEntity) {
        return HotelMapper.INSTANCE.hotelEntityToHotel(hotelEntity);
    }
}

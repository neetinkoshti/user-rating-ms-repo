package com.ntn.hotel.service.hotelservice.service;


import com.ntn.hotel.service.hotelservice.entity.HotelEntity;
import com.ntn.hotel.service.hotelservice.model.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HotelMapper {

    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);
    Hotel hotelEntityToHotel(HotelEntity hotelEntity);

    HotelEntity hotelToHotelEntity(Hotel hotel);

}

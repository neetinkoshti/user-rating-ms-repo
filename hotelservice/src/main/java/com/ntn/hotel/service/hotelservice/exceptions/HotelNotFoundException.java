package com.ntn.hotel.service.hotelservice.exceptions;

public class HotelNotFoundException extends RuntimeException{

    public HotelNotFoundException(String message) {
        super(message);
    }

    public HotelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

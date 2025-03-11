package com.ntn.hotel.service.hotelservice.exceptions;

import com.ntn.hotel.service.hotelservice.payload.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(HotelNotFoundException.class)
    public ResponseEntity<APIResponse> handleHotelNotFoundException(HotelNotFoundException ex) {

        APIResponse response = new APIResponse.APIResponseBuilder()
                .message(ex.getMessage())
                .success(false)
                .status(org.springframework.http.HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(response, org.springframework.http.HttpStatus.NOT_FOUND);
    }

}

package com.ntn.hotel.service.hotelservice.controller;

import com.ntn.hotel.service.hotelservice.model.Hotel;
import com.ntn.hotel.service.hotelservice.payload.APIResponse;
import com.ntn.hotel.service.hotelservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

     @Autowired
     private HotelService hotelService;

     @GetMapping("/allHotels")
     public ResponseEntity<List<Hotel>> getAllHotels() {
         List<Hotel> hotels = hotelService.getHotels();
         return new ResponseEntity<>(hotels, HttpStatus.OK);
     }

     @GetMapping("hotelById/{id}")
     public ResponseEntity<Hotel> getHotelById(@PathVariable("id") String id) {
         Hotel hotel = hotelService.getHotel(id);
         return new ResponseEntity<>(hotel, HttpStatus.OK);
     }

     @PostMapping("/createHotel")
     public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
         Hotel createdHotel = hotelService.addHotel(hotel);
         return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
     }

     @PutMapping("updateHotel/{id}")
     public ResponseEntity<Hotel> updateHotel(@PathVariable String id, @RequestBody Hotel hotel) {
         Hotel updatedHotel = hotelService.updateHotel(id, hotel);
         return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
     }

     @DeleteMapping("deleteHotel/{id}")
     public ResponseEntity<APIResponse> deleteHotel(@PathVariable("id") String id) {
         hotelService.deleteHotel(id);
         return ResponseEntity.ok(new APIResponse.APIResponseBuilder()
                 .success(true)
                 .message("Hotel deleted successfully")
                 .status(HttpStatus.OK)
                 .build());
     }


}

package com.ntn.user.service.userservice.exceptions;


import com.ntn.user.service.userservice.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        return new ResponseEntity<APIResponse>(
                        new APIResponse.APIResponseBuilder()
                .message(ex.getMessage())
                .success(true).status(HttpStatus.NOT_FOUND)
                .build(), HttpStatus.NOT_FOUND);
    }
}

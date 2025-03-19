package com.ntn.auth.service.exceptions;

import com.ntn.auth.service.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthResponse> handleBadCredentialsException(BadCredentialsException ex) {

        return new ResponseEntity<>(new AuthResponse(ex.getMessage() ,true ,
                HttpStatus.UNAUTHORIZED)
                , HttpStatus.UNAUTHORIZED);
    }

}

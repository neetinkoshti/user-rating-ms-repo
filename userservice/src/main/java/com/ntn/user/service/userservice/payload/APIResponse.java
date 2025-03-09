package com.ntn.user.service.userservice.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
//@Builder
public class APIResponse {

    private String message;
    private boolean success;
    private HttpStatus status;

    public APIResponse(String message, boolean success, HttpStatus status) {
        this.message = message;
        this.success = success;
        this.status = status;
    }

    public static class APIResponseBuilder{
        private String message;
        private boolean success;
        private HttpStatus status;

        public APIResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public APIResponseBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public APIResponseBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public APIResponse build() {
            return new APIResponse(message, success, status);
        }
    }

}

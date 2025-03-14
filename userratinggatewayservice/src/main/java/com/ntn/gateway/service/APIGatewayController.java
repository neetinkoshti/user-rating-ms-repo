package com.ntn.gateway.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gateway")
public class APIGatewayController {

    @GetMapping("/test")
    public String test(){
        return "Gateway Service is up and running";
    }
}

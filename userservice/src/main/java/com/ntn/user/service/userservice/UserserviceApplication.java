package com.ntn.user.service.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserserviceApplication.class, args);
	}


}

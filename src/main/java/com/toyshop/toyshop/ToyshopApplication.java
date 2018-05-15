package com.toyshop.toyshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class ToyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyshopApplication.class, args);
	}

}

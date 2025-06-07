package com.accessed.miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AccessedMiniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessedMiniProjectApplication.class, args);
	}

}

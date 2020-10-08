package com.fpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CapstoneProjectManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapstoneProjectManagementApplication.class, args);
	}

}

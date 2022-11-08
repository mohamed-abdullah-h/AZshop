package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class AzShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzShopApplication.class, args);
	}

}

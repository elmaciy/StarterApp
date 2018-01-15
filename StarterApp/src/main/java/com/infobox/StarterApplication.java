package com.infobox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.infobox.controller","com.infobox.service"})
@EnableCaching
public class StarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args); 
	}
}

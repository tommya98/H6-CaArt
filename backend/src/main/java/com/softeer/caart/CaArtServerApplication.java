package com.softeer.caart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class CaArtServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaArtServerApplication.class, args);
	}

}

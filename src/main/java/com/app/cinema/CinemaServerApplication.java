package com.app.cinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.app.cinema.repository")
@EnableConfigurationProperties
public class CinemaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaServerApplication.class, args);
	}

}

package com.bookx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookXApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookXApplication.class, args);
	}

}

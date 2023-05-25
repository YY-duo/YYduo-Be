package com.YYduo.KkuldongVarietyStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KkuldongVarietyStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(KkuldongVarietyStoreApplication.class, args);
	}

}

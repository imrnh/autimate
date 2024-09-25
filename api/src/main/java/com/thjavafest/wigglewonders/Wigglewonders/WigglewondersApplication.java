package com.thjavafest.wigglewonders.Wigglewonders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.thjavafest.wigglewonders.Wigglewonders.repo")
public class WigglewondersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WigglewondersApplication.class, args);
	}

}

package com.faraday.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@EnableCaching
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
	}

}

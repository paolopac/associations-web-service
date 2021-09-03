package com.faraday.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
	}

}

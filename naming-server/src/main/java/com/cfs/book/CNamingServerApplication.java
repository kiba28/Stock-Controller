package com.cfs.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CNamingServerApplication.class, args);
	}

}

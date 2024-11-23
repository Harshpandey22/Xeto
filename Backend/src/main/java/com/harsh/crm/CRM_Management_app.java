package com.harsh.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.harsh.crm.repository.mongo")
@EnableJpaRepositories(basePackages = "com.harsh.crm.repository.jpa")
public class CRM_Management_app {

	public static void main(String[] args) {
		SpringApplication.run(CRM_Management_app.class, args);
	}

}
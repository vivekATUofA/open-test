package com.optimagrowth.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.optimagrowth.license.model")  // Scanning the model package for entities
@EnableJpaRepositories(basePackages = "com.optimagrowth.license.repository")  // Scanning the repository package
public class LicenseServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LicenseServiceApplication.class, args);
	}
}

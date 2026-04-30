package com.example.docker_demo_springboot_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DockerDemoSpringbootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerDemoSpringbootAppApplication.class, args);
	}

}

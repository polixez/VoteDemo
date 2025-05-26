package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Аннотация @SpringBootApplication включает в себя @Configuration, @EnableAutoConfiguration и @ComponentScan
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}


//http://localhost:8080/register
//Korotenko Ivan qwerty123

//http://localhost:8080/h2-console
//JDBC URL: jdbc:h2:mem:testdb
//SELECT * FROM users;
//SELECT * FROM votes;
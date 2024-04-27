package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses=ClientController.class)
public class MyMain1Application {

	public static void main(String[] args) {
		SpringApplication.run(MyMain1Application.class, args);
	}

}

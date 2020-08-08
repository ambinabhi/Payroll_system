package com.fileee.payrole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fileee.payrole"})
public class PayroleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayroleApplication.class, args);
	}
	
}
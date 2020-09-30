package com.zabud.email.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmailReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailReceiverApplication.class, args);
		System.out.print("EmailReceiverApplication");
	}

}

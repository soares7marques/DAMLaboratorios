package com.example.meu_primeiroSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example", "com.example.meu_primeiroSpringBoot"})
public class MeuPrimeiroSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeuPrimeiroSpringBootApplication.class, args);
	}
}

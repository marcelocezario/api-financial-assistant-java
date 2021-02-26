package br.dev.mhc.financialassistantapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialAssistantApiApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(FinancialAssistantApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}

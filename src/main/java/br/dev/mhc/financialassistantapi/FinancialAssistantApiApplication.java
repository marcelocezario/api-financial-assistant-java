package br.dev.mhc.financialassistantapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dev.mhc.financialassistantapi.services.SeedDBService;

@SpringBootApplication
public class FinancialAssistantApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FinancialAssistantApiApplication.class, args);
	}
	
	@Autowired
	SeedDBService seedService;

	@Override
	public void run(String... args) throws Exception {
		
		seedService.seedUser();
		seedService.seedCategory();
		seedService.seedCurrencyType();
		
	}
}

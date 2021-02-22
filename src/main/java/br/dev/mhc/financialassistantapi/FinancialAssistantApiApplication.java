package br.dev.mhc.financialassistantapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.services.UserService;

@SpringBootApplication
public class FinancialAssistantApiApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(FinancialAssistantApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User marcelo = userService.findByEmail("marcelo@gmail.com");
		System.out.println(marcelo);
		
		User teste = userService.findByEmail("testeteste@gmail.com");
		System.out.println(teste);
		
		
	}

}

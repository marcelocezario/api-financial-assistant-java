package br.dev.mhc.financialassistantapi.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.dev.mhc.financialassistantapi.services.DBService;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.email.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbService.databaseSeeding();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
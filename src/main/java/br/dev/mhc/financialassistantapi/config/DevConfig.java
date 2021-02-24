package br.dev.mhc.financialassistantapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.email.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
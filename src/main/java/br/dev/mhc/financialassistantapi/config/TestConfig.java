package br.dev.mhc.financialassistantapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.email.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}

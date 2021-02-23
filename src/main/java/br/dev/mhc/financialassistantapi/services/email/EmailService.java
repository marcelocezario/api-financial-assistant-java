package br.dev.mhc.financialassistantapi.services.email;

import org.springframework.mail.SimpleMailMessage;

import br.dev.mhc.financialassistantapi.entities.User;

public interface EmailService {
	
	void sendUserConfirmationEmail(User obj);
	
	void sendEmail(SimpleMailMessage msg);
}
package br.dev.mhc.financialassistantapi.services.email;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.dev.mhc.financialassistantapi.entities.User;

public interface EmailService {

	void sendUserConfirmationEmail(User obj);

	void sendEmail(SimpleMailMessage msg);

	void sendUserConfirmationHtmlEmail(User obj);

	void sendHtmlEmail(MimeMessage msg);
}
package br.dev.mhc.financialassistantapi.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new ObjectNotFoundException("Email not found");
		}
		String newPass = newPassword();
		user.setPassword(pe.encode(newPass));
		
		userRepository.save(user);
		emailService.sendNewPasswordEmail(user, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		//gera um digito
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}
		//gera letra maiúscula
		else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		//gera letra minúscula
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}

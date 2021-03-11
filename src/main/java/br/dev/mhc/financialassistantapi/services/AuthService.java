package br.dev.mhc.financialassistantapi.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.exceptions.AuthorizationException;
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

	public static UserSpringSecurity getAuthenticatedUserSpringSecurity() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * En: It receives as parameter the id of the user who is authorized (not to be
	 * confused with an authenticated user) and the type of authorization. If the
	 * user is not authorized, the method will return an AuthorizationException.
	 * 
	 * Pt: Recebe como parametro o id do usuário que é autorizado (não confundir com
	 * usuário já autenticado) e o tipo de autorização. Se o usuário não for
	 * autorizado, o método retornará uma AuthorizationException.
	 * 
	 * @param authorizedUserId
	 * @param authorizationType
	 */
	public static void validatesUserAuthorization(Long authorizedUserId, AuthorizationType authorizationType) {
		UserSpringSecurity userSS = getAuthenticatedUserSpringSecurity();

		switch (authorizationType) {
		case ADMIN_ONLY:
			if (userSS == null || !userSS.hasRole(Profile.ADMIN)) {
				throw new AuthorizationException("Access denied");
			}
			break;

		case USER_ONLY:
			if (userSS == null || !authorizedUserId.equals(userSS.getId())) {
				throw new AuthorizationException("Access denied");
			}
			break;

		case USER_OR_ADMIN:
			if (userSS == null || !userSS.hasRole(Profile.ADMIN) && !authorizedUserId.equals(userSS.getId())) {
				throw new AuthorizationException("Access denied");
			}
			break;

		default:
			throw new AuthorizationException("Access denied");
		}
	}

	public void sendNewPassword(String email) {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("Email not found");
		}
		String newPass = newPassword();
		user.setPassword(pe.encode(newPass));

		userRepository.save(user);
		emailService.sendNewPasswordEmail(user, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);

		// gera um digito
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		}
		// gera letra maiúscula
		else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		}
		// gera letra minúscula
		else {
			return (char) (rand.nextInt(26) + 97);
		}
	}
}

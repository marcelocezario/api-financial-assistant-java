package br.dev.mhc.financialassistantapi.services;

import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;

	public void instantiateTestDatabase() throws ParseException {

		User u1 = new User(null, "Marcelo", "marcelo@gmail.com", pe.encode("111111"),
				Instant.parse("2021-02-01T10:00:00Z"), Instant.parse("2021-02-01T10:01:00Z"), true);
		u1.addProfile(Profile.ADMIN);
		
		User u2 = new User(null, "Jenni", "jenni@gmail.com", pe.encode("222222"), Instant.parse("2021-02-02T10:00:00Z"),
				Instant.parse("2021-02-02T10:01:00Z"), true);
		User u3 = new User(null, "Dekhan", "dekhan@gmail.com", pe.encode("333333"),
				Instant.parse("2021-02-03T10:00:00Z"), Instant.parse("2021-02-03T10:01:00Z"), true);
		User u4 = new User(null, "blk", "blk@gmail.com", pe.encode("444444"), Instant.parse("2021-02-04T10:00:00Z"),
				Instant.parse("2021-02-04T10:01:00Z"), true);
		User u5 = new User(null, "Bruno", "bruno@gmail.com", pe.encode("555555"), Instant.parse("2021-02-05T10:00:00Z"),
				Instant.parse("2021-02-05T10:01:00Z"), true);
		User u6 = new User(null, "Carlos", "carlos@gmail.com", pe.encode("666666"),
				Instant.parse("2021-02-06T10:00:00Z"), Instant.parse("2021-02-06T10:01:00Z"), true);

		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));
	}

}

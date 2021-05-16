package br.dev.mhc.financialassistantapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSpringSecurity(user.getId(), user.getUuid(), user.getEmail(), user.getPassword(), user.getProfiles());
	}
}

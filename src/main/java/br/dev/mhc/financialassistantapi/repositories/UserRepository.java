package br.dev.mhc.financialassistantapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Transactional(readOnly = true)
	User findByEmail(String email);

	Optional<User> findByUuid(String uuid);
}

package br.dev.mhc.financialassistantapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT a FROM Account a WHERE a.user = :user")
	List<Account> findByUser(User user);
}

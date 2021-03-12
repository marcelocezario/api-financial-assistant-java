package br.dev.mhc.financialassistantapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.User;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

	@Transactional(readOnly = true)
	List<Entry> findByAccountOrderByPaymentMomentDesc(Account account);

	@Transactional(readOnly = true)
	Page<Entry> findByAccount(Account account, Pageable pageRequest);

	@Transactional(readOnly = true)
	List<Entry> findByUserAndAccountIsNullOrderByDueDateDesc(User user);

	@Transactional(readOnly = true)
	Page<Entry> findByUserAndAccountIsNull(User user, Pageable pageRequest);
}

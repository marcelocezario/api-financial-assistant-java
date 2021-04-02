package br.dev.mhc.financialassistantapi.repositories;

import java.util.List;
import java.util.Optional;

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
	List<Entry> findByUserAndAccountIsNullOrderByDueDateAsc(User user);

	@Transactional(readOnly = true)
	Page<Entry> findByUserAndAccountIsNull(User user, Pageable pageRequest);

	@Transactional(readOnly = true)
	List<Entry> findByUserAndPaymentMomentIsNullOrderByDueDateAsc(User user);

	@Transactional(readOnly = true)
	Page<Entry> findByUserAndPaymentMomentIsNull(User user, Pageable pageRequest);

	Optional<Entry> findByUuid(String uuid);
}

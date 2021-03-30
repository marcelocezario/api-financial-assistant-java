package br.dev.mhc.financialassistantapi.repositories;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.CurrencyType;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long> {

	@Transactional(readOnly = true)
	CurrencyType findByCode(String code);

	@Query("SELECT max(ct.lastUpdate) FROM CurrencyType ct")
	Instant findMaxInstantLastUpdate();
}

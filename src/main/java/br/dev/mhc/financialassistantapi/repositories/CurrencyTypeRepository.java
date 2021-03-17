package br.dev.mhc.financialassistantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.mhc.financialassistantapi.entities.CurrencyType;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, String> {

}

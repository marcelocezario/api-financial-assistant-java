package br.dev.mhc.financialassistantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.mhc.financialassistantapi.entities.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

}

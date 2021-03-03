package br.dev.mhc.financialassistantapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.dev.mhc.financialassistantapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

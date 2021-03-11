package br.dev.mhc.financialassistantapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.entities.User;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Transactional(readOnly = true)
	List<Category> findByUserOrderByNameAsc(User user);

	@Transactional(readOnly = true)
	Page<Category> findByUser(User user, Pageable pageRequest);
}

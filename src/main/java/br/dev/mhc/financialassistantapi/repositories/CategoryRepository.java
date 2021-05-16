package br.dev.mhc.financialassistantapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.entities.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Transactional(readOnly = true)
	List<Category> findByUserOrDefaultForAllUsersTrueOrderByNameAsc(User user);

	@Transactional(readOnly = true)
	Page<Category> findByUserOrDefaultForAllUsersTrue(User user, Pageable pageRequest);

	Optional<Category> findByUuid(String uuid);
}

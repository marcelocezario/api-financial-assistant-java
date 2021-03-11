package br.dev.mhc.financialassistantapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.CategoryDTO;
import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.repositories.CategoryRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private UserService userService;

	@Transactional
	public Category insert(Category obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		obj.setId(null);
		obj.setUser(userService.findById(userSS.getId()));
		obj = repository.save(obj);
		return obj;
	}

	public List<Category> findByUser() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		return repository.findByUserOrderByNameAsc(userService.findById(userSS.getId()));
	}

	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category category = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Category.class.getName()));
		AuthService.validatesUserAuthorization(category.getUser().getId(), AuthorizationType.USER_ONLY);
		return category;
	}

	public Page<Category> findPageByUser(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByUser(userService.findById(userSS.getId()), pageRequest);
	}

	@Transactional
	public Category update(Category obj) {
		Category newObj = findById(obj.getId());
		AuthService.validatesUserAuthorization(newObj.getUser().getId(), AuthorizationType.USER_ONLY);
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Category newObj, Category obj) {
		newObj.setName(obj.getName());
		newObj.setIconUrl(obj.getIconUrl());
	}

	public Category fromDTO(CategoryDTO objDTO) {
		Category category = new Category(objDTO.getId(), objDTO.getName(), objDTO.getIconUrl(), null);
		return category;
	}

}

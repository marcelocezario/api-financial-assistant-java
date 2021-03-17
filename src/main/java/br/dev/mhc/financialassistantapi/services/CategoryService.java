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
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);

		obj.setId(null);
		obj.setUser(userService.findById(userSS.getId()));
		obj = repository.save(obj);
		return obj;
	}

	public List<Category> findByUserAndDefault() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		return repository.findByUserOrDefaultForAllUsersTrueOrderByNameAsc(userService.findById(userSS.getId()));
	}

	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category category = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + Category.class.getName()));
		if (!category.isDefaultForAllUsers()) {
			AuthService.validatesUserAuthorization(category.getUser().getId(), AuthorizationType.USER_ONLY);
		}
		return category;
	}

	public Page<Category> findPageByUserAndDefault(Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByUserOrDefaultForAllUsersTrue(userService.findById(userSS.getId()), pageRequest);
	}

	@Transactional
	public Category update(Category obj) {
		Category newObj = findById(obj.getId());
		if (newObj.isDefaultForAllUsers()) {
			UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
			AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
			updateData(newObj, obj);
			newObj.setUser(userService.findById(userSS.getId()));
			return repository.save(newObj);
		} else {
			AuthService.validatesUserAuthorization(newObj.getUser().getId(), AuthorizationType.USER_ONLY);
			updateData(newObj, obj);
			return repository.save(newObj);
		}
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

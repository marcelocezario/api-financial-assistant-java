package br.dev.mhc.financialassistantapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.dto.UserDTO;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}

	public User insert(User obj) {
		obj = repository.save(obj);
		return obj;
	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void updateData(User newObj, User obj) {
		newObj.setNickname(obj.getNickname());
		newObj.setEmail(obj.getEmail());
	}

	public User fromDTO(UserDTO objDTO) {
		return new User(objDTO.getId(), objDTO.getNickname(), objDTO.getEmail(), null, objDTO.getRegistrationDate(),
				objDTO.getLastAccess(), objDTO.isActive());
	}
	
}

package br.dev.mhc.financialassistantapi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.dev.mhc.financialassistantapi.dto.UserNewDTO;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.resources.exceptions.FieldMessage;
import br.dev.mhc.financialassistantapi.services.UserService;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

	@Autowired
	private UserService repository;

	@Override
	public void initialize(UserInsert ann) {
	}

	@Override
	public boolean isValid(UserNewDTO objDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		User aux = repository.findByEmail(objDTO.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "E-mail already registered"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}

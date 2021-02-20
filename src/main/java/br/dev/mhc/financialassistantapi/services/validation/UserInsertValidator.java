package br.dev.mhc.financialassistantapi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.resources.exceptions.FieldMessage;

public class UserInsertValidator implements ConstraintValidator<UserInsert, User> {
	
	@Override
	public void initialize(UserInsert ann) {
	}

	@Override
	public boolean isValid(User obj, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		// Incluir os testes aqui, inserindo erros na lista
		
		// Exemplo
		if (obj.getEmail() == null) {
			list.add(new FieldMessage("email", "Email cannot be null"));
		}
		
		
		// Adicionando erros personalizados na sintaxe do framework
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
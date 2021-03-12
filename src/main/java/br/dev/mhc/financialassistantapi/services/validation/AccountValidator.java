package br.dev.mhc.financialassistantapi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.dev.mhc.financialassistantapi.dto.AccountDTO;
import br.dev.mhc.financialassistantapi.resources.exceptions.FieldMessage;

public class AccountValidator implements ConstraintValidator<Account, AccountDTO> {

	@Override
	public void initialize(Account ann) {
	}

	@Override
	public boolean isValid(AccountDTO objDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDTO.getAccountType() == null) {
			list.add(new FieldMessage("accountType", "Entrytype cannot be null"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}

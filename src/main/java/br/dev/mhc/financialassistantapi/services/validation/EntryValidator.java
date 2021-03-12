package br.dev.mhc.financialassistantapi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.dev.mhc.financialassistantapi.dto.EntryDTO;
import br.dev.mhc.financialassistantapi.resources.exceptions.FieldMessage;

public class EntryValidator implements ConstraintValidator<Entry, EntryDTO> {

	@Override
	public void initialize(Entry ann) {
	}

	@Override
	public boolean isValid(EntryDTO objDTO, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDTO.getAccount() == null && objDTO.getPaymentMoment() != null) {
			list.add(new FieldMessage("account",
					"To register an entry with a payment date, an account must be entered"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}

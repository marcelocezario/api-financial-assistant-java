package br.dev.mhc.financialassistantapi.services;

import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.dto.CurrencyTypeDTO;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;

@Service
public class CurrencyTypeService {

	public CurrencyType fromDTO(CurrencyTypeDTO objDTO) {
		return new CurrencyType(objDTO.getId(), objDTO.getCode(), objDTO.getName(), objDTO.getInitials(),
				objDTO.getDecimalDigits());
	}
}

package br.dev.mhc.financialassistantapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.CurrencyType;

@Service
public class DefaultService {

	@Autowired
	private CurrencyTypeService currencyService;

	public CurrencyType currencyDefault() {
		return currencyService.findByCode("BRL");
	}
}

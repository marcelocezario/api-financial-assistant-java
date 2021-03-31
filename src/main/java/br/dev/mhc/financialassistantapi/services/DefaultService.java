package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.accounts.InvestmentAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.Wallet;

@Service
public class DefaultService {

	@Autowired
	private CurrencyTypeService currencyService;
	
	public CurrencyType currencyDefault() {
		return currencyService.findByCode("BRL");
	}
	
	public List<Account> accountsDefault() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Wallet(null, "Carteira", BigDecimal.ZERO, currencyDefault(), null));
		accounts.add(new BankAccount(null, "Conta corrente", BigDecimal.ZERO, currencyDefault(), BigDecimal.ZERO, BigDecimal.ZERO, null));
		accounts.add(new CreditCard(null, "Cartão de crédito", BigDecimal.ZERO, currencyDefault(), 1, 10, BigDecimal.ZERO, null));
		accounts.add(new InvestmentAccount(null, "Investimento", BigDecimal.ZERO, currencyDefault(), null));
		return accounts;
	}
}

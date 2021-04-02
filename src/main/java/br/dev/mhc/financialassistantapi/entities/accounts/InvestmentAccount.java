package br.dev.mhc.financialassistantapi.entities.accounts;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_investment_account")
public class InvestmentAccount extends Account {

	private static final long serialVersionUID = 1L;

	public InvestmentAccount() {
		this.setAccountType(AccountType.INVESTMENT_ACCOUNT);
	}

	public InvestmentAccount(Long id, String uuid, String name, BigDecimal balance, CurrencyType currencyType, User user) {
		super(id, uuid, name, balance, AccountType.INVESTMENT_ACCOUNT, currencyType, user);
	}
}

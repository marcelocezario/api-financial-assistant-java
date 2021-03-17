package br.dev.mhc.financialassistantapi.entities.accounts;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_wallet")
public class Wallet extends Account {

	private static final long serialVersionUID = 1L;

	public Wallet() {
		this.setAccountType(AccountType.WALLET);
	}

	public Wallet(Long id, String name, BigDecimal balance, CurrencyType currencyType, User user) {
		super(id, name, balance, AccountType.WALLET, currencyType, user);
	}
}

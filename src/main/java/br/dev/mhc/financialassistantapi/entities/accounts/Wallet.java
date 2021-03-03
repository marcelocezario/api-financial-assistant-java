package br.dev.mhc.financialassistantapi.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_wallet")
@JsonTypeName("wallet")
public class Wallet extends Account {

	private static final long serialVersionUID = 1L;

	public Wallet() {
		this.setAccountType(AccountType.WALLET);
	}

	public Wallet(Long id, String name, Double balance) {
		super(id, name, balance, AccountType.WALLET);
	}
}

package br.dev.mhc.financialassistantapi.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;

@Entity
@Table(name = "tb_wallet")
public class Wallet extends Account {

	private static final long serialVersionUID = 1L;

	public Wallet() {
	}

	public Wallet(Long id, String name, Double balance) {
		super(id, name, balance);
	}
}

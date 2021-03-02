package br.dev.mhc.financialassistantapi.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;

@Entity
@Table(name = "tb_bank_account")
public class BankAccount extends Account {
	
	private static final long serialVersionUID = 1L;

	private Double bankInterestRate;
	private Double valueLimit;
	
	public BankAccount() {
	}

	public BankAccount(Long id, String name, Double balance, Double bankInterestRate, Double valueLimit) {
		super(id, name, balance);
		this.bankInterestRate = bankInterestRate;
		this.valueLimit = valueLimit;
	}

	public Double getBankInterestRate() {
		return bankInterestRate;
	}

	public void setBankInterestRate(Double bankInterestRate) {
		this.bankInterestRate = bankInterestRate;
	}

	public Double getValueLimit() {
		return valueLimit;
	}

	public void setValueLimit(Double valueLimit) {
		this.valueLimit = valueLimit;
	}
}

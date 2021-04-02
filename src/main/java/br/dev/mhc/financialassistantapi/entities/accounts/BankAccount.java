package br.dev.mhc.financialassistantapi.entities.accounts;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_bank_account")
public class BankAccount extends Account {

	private static final long serialVersionUID = 1L;

	private BigDecimal bankInterestRate;
	private BigDecimal limitValueBankAccount;

	public BankAccount() {
		this.setAccountType(AccountType.BANK_ACCOUNT);
	}

	public BankAccount(Long id, String uuid, String name, BigDecimal balance, CurrencyType currencyType, BigDecimal bankInterestRate,
			BigDecimal limitValueBankAccount, User user) {
		super(id, uuid, name, balance, AccountType.BANK_ACCOUNT, currencyType, user);
		this.bankInterestRate = bankInterestRate;
		this.limitValueBankAccount = limitValueBankAccount;
	}

	public BigDecimal getBankInterestRate() {
		return bankInterestRate;
	}

	public void setBankInterestRate(BigDecimal bankInterestRate) {
		this.bankInterestRate = bankInterestRate;
	}

	public BigDecimal getLimitValueBankAccount() {
		return limitValueBankAccount;
	}

	public void setLimitValue(BigDecimal limitValueBankAccount) {
		this.limitValueBankAccount = limitValueBankAccount;
	}
}

package br.dev.mhc.financialassistantapi.entities.accounts;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_bank_account")
public class BankAccount extends Account {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Bank interest rate is a required field")
	@PositiveOrZero(message = "The bank interest rate must be greater than or equal to zero")
	private BigDecimal bankInterestRate;

	@NotNull(message = "Limit value is a required field")
	@PositiveOrZero(message = "The limit value must be greater than or equal to zero")
	private BigDecimal limitValueBankAccount;

	public BankAccount() {
		this.setAccountType(AccountType.BANK_ACCOUNT);
	}

	public BankAccount(Long id, String name, BigDecimal balance, BigDecimal bankInterestRate, BigDecimal limitValueBankAccount,
			User user) {
		super(id, name, balance, AccountType.BANK_ACCOUNT, user);
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

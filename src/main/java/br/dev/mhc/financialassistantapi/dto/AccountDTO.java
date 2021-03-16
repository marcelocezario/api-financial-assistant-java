package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@br.dev.mhc.financialassistantapi.services.validation.Account
public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Account
	private Long id;
	
	@NotEmpty(message = "The account's name is a required field")
	@Length(max = 50, message = "Maximum number of 50 characters exceeded")
	private String name;
	private BigDecimal balance;
	
	@NotNull(message = "Entrytype cannot be null")
	private AccountType accountType;

	// Wallet
	// Não possui atributos exclusivos

	// BankAccount
	@NotNull(message = "Bank interest rate is a required field")
	@PositiveOrZero(message = "The bank interest rate must be greater than or equal to zero")
	private BigDecimal bankInterestRate;
	@NotNull(message = "Limit value is a required field")
	@PositiveOrZero(message = "The limit value must be greater than or equal to zero")
	private BigDecimal limitValueBankAccount;

	// CreditCard
	@NotNull(message = "Closing day is a required field")
	@Min(value = 1, message = "Choose a day between 1 and 31")
	@Max(value = 31, message = "Choose a day between 1 and 31")
	private Integer closingDay;
	@NotNull(message = "Due day is a required field")
	@Min(value = 1, message = "Choose a day between 1 and 31")
	@Max(value = 31, message = "Choose a day between 1 and 31")
	private Integer dueDay;
	@NotNull(message = "Limit value is a required field")
	@PositiveOrZero(message = "The limit value must be greater than or equal to zero")
	private BigDecimal limitValueCard;

	// InvestmentAccount
	// Não possui atributos exclusivos

	public AccountDTO() {
	}

	public AccountDTO(Long id, String name, BigDecimal balance, AccountType accountType,
			BigDecimal bankInterestRate, BigDecimal limitValueBankAccount, Integer closingDay, Integer dueDay,
			BigDecimal limitValueCard) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
		this.accountType = accountType;
		this.bankInterestRate = bankInterestRate;
		this.limitValueBankAccount = limitValueBankAccount;
		this.closingDay = closingDay;
		this.dueDay = dueDay;
		this.limitValueCard = limitValueCard;
	}

	public AccountDTO(Account obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.balance = obj.getBalance();
		this.accountType = obj.getAccountType();
		
		switch (obj.getAccountType()) {
		case WALLET:
			break;
		case BANK_ACCOUNT:
			this.bankInterestRate = ((BankAccount)obj).getBankInterestRate();
			this.limitValueBankAccount = ((BankAccount)obj).getLimitValueBankAccount();
			break;
		case CREDIT_CARD:
			this.closingDay = ((CreditCard)obj).getClosingDay();
			this.dueDay = ((CreditCard)obj).getDueDay();
			this.limitValueCard = ((CreditCard)obj).getLimitValueCard();
			break;
		case INVESTMENT_ACCOUNT:
			break;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
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

	public void setLimitValueBankAccount(BigDecimal limitValueBankAccount) {
		this.limitValueBankAccount = limitValueBankAccount;
	}

	public Integer getClosingDay() {
		return closingDay;
	}

	public void setClosingDay(Integer closingDay) {
		this.closingDay = closingDay;
	}

	public Integer getDueDay() {
		return dueDay;
	}

	public void setDueDay(Integer dueDay) {
		this.dueDay = dueDay;
	}

	public BigDecimal getLimitValueCard() {
		return limitValueCard;
	}

	public void setLimitValueCard(BigDecimal limitValueCard) {
		this.limitValueCard = limitValueCard;
	}
}

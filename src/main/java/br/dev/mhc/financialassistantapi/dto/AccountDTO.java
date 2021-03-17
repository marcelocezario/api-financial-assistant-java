package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Account
	private Long id;
	private String name;
	private BigDecimal balance;

	private AccountType accountType;
	private CurrencyTypeDTO currencyType;

	// Wallet
	// Não possui atributos exclusivos

	// BankAccount
	private BigDecimal bankInterestRate;
	private BigDecimal limitValueBankAccount;

	// CreditCard
	private Integer closingDay;
	private Integer dueDay;
	private BigDecimal limitValueCard;

	// InvestmentAccount
	// Não possui atributos exclusivos

	public AccountDTO() {
	}

	public AccountDTO(Long id, String name, BigDecimal balance, AccountType accountType, CurrencyTypeDTO currencyType,
			BigDecimal bankInterestRate, BigDecimal limitValueBankAccount, Integer closingDay, Integer dueDay,
			BigDecimal limitValueCard) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
		this.accountType = accountType;
		this.currencyType = currencyType;
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
		this.currencyType = new CurrencyTypeDTO(obj.getCurrencyType());

		switch (obj.getAccountType()) {
		case WALLET:
			break;
		case BANK_ACCOUNT:
			this.bankInterestRate = ((BankAccount) obj).getBankInterestRate();
			this.limitValueBankAccount = ((BankAccount) obj).getLimitValueBankAccount();
			break;
		case CREDIT_CARD:
			this.closingDay = ((CreditCard) obj).getClosingDay();
			this.dueDay = ((CreditCard) obj).getDueDay();
			this.limitValueCard = ((CreditCard) obj).getLimitValueCard();
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

	public CurrencyTypeDTO getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyTypeDTO currencyType) {
		this.currencyType = currencyType;
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

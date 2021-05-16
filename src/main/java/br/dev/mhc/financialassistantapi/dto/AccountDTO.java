package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Account
	private String uuid;
	private String name;
	private BigDecimal balance;

	private AccountType accountType;
	private Instant creationMoment;
	private Instant lastUpdate;

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

	public AccountDTO(String uuid, String name, BigDecimal balance, AccountType accountType, Instant creationMoment,
			Instant lastUpdate, CurrencyTypeDTO currencyType, BigDecimal bankInterestRate,
			BigDecimal limitValueBankAccount, Integer closingDay, Integer dueDay, BigDecimal limitValueCard) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.balance = balance;
		this.accountType = accountType;
		this.creationMoment = creationMoment;
		this.lastUpdate = lastUpdate;
		this.currencyType = currencyType;
		this.bankInterestRate = bankInterestRate;
		this.limitValueBankAccount = limitValueBankAccount;
		this.closingDay = closingDay;
		this.dueDay = dueDay;
		this.limitValueCard = limitValueCard;
	}

	public AccountDTO(Account obj) {
		this.uuid = obj.getUuid();
		this.name = obj.getName();
		this.balance = obj.getBalance();
		this.accountType = obj.getAccountType();
		this.creationMoment = obj.getCreationMoment();
		this.lastUpdate = obj.getLastUpdate();
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public Instant getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Instant creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
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

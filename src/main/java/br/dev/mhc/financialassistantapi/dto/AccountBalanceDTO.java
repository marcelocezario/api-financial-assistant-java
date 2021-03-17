package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class AccountBalanceDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	BigDecimal balance;
	
	public AccountBalanceDTO() {
	}
	
	public AccountBalanceDTO(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}

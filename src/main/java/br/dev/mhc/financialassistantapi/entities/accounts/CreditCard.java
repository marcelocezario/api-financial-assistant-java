package br.dev.mhc.financialassistantapi.entities.accounts;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

@Entity
@Table(name = "tb_credit_card")
public class CreditCard extends Account {

	private static final long serialVersionUID = 1L;

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

	public CreditCard() {
		this.setAccountType(AccountType.CREDIT_CARD);
	}

	public CreditCard(Long id, String name, BigDecimal balance, CurrencyType currencyType, Integer closignDay,
			Integer dueDay, BigDecimal limitValueCard, User user) {
		super(id, name, balance, AccountType.CREDIT_CARD, currencyType, user);
		this.closingDay = closignDay;
		this.dueDay = dueDay;
		this.limitValueCard = limitValueCard;
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

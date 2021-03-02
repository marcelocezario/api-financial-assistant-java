package br.dev.mhc.financialassistantapi.entities.accounts;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.dev.mhc.financialassistantapi.entities.Account;

@Entity
@Table(name = "tb_credit_card")
public class CreditCard extends Account {

	private static final long serialVersionUID = 1L;

	private Integer closingDay;
	private Integer dueDay;
	private Double limitValue;

	public CreditCard() {
	}

	public CreditCard(Long id, String name, Double balance, Integer closignDay, Integer dueDay, Double limitValue) {
		super(id, name, balance);
		this.closingDay = closignDay;
		this.dueDay = dueDay;
		this.limitValue = limitValue;
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

	public Double getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(Double limitValue) {
		this.limitValue = limitValue;
	}
}

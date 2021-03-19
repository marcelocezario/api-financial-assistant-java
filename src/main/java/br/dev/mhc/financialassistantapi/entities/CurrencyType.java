package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_currency_use")
public class CurrencyType implements Serializable {

	private static final long serialVersionUID = 1L;

	// dados da ISO4217
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String code;
	private String name;
	private String initials;
	private Integer decimalDigits;

	@Column(scale = 5)
	private BigDecimal priceInBRL;
	private Instant lastUpdate;

	public CurrencyType() {
	}

	public CurrencyType(Integer id, String code, String name, String initials, Integer decimalDigits,
			BigDecimal priceInBRL, Instant lastUpdate) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.initials = initials;
		this.decimalDigits = decimalDigits;
		this.priceInBRL = priceInBRL;
		this.lastUpdate = (lastUpdate.compareTo(Instant.now()) < 0) ? lastUpdate : Instant.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public BigDecimal getPriceInBRL() {
		return priceInBRL;
	}

	public void setPriceInBRL(BigDecimal priceInBRL) {
		this.priceInBRL = priceInBRL;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}

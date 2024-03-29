package br.dev.mhc.financialassistantapi.dto;

import java.math.BigDecimal;
import java.time.Instant;

import br.dev.mhc.financialassistantapi.entities.CurrencyType;

public class CurrencyTypeDTO {

	private String uuid;
	private String code;
	private String name;
	private String initials;
	private Integer decimalDigits;
	private BigDecimal priceInBRL;
	private Instant lastUpdate;

	public CurrencyTypeDTO() {
	}

	public CurrencyTypeDTO(String uuid, String code, String name, String initials, Integer decimalDigits,
			BigDecimal priceInBRL, Instant lastUpdate) {
		super();
		this.uuid = uuid;
		this.code = code;
		this.name = name;
		this.initials = initials;
		this.decimalDigits = decimalDigits;
		this.priceInBRL = priceInBRL;
		this.lastUpdate = lastUpdate;
	}

	public CurrencyTypeDTO(CurrencyType obj) {
		this.uuid = obj.getUuid();
		this.code = obj.getCode();
		this.name = obj.getName();
		this.initials = obj.getInitials();
		this.decimalDigits = obj.getDecimalDigits();
		this.priceInBRL = obj.getPriceInBRL();
		this.lastUpdate = obj.getLastUpdate();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

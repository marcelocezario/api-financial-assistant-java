package br.dev.mhc.financialassistantapi.services.hgservice.model;

import java.math.BigDecimal;

public class HGCurrencyModel {

	private String name;
	private BigDecimal buy;
	private BigDecimal sell;
	private BigDecimal variation;
	private String code;
	
	public HGCurrencyModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getBuy() {
		return buy;
	}

	public void setBuy(BigDecimal buy) {
		this.buy = buy;
	}

	public BigDecimal getSell() {
		return sell;
	}

	public void setSell(BigDecimal sell) {
		this.sell = sell;
	}

	public BigDecimal getVariation() {
		return variation;
	}

	public void setVariation(BigDecimal variation) {
		this.variation = variation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

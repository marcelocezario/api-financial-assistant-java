package br.dev.mhc.financialassistantapi.services.hgservice.model;

import java.math.BigDecimal;

public class HGStockModel {
	
	private String name;
	private String location;
	private BigDecimal points;
	private BigDecimal variation;
	
	public HGStockModel() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getVariation() {
		return variation;
	}

	public void setVariation(BigDecimal variation) {
		this.variation = variation;
	}
}

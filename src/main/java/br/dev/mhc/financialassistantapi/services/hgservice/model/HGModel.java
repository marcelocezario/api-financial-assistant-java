package br.dev.mhc.financialassistantapi.services.hgservice.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class HGModel {

	private String by;
	private boolean valid_key;
	private HGResultsModel results;

	public HGModel() {
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public boolean isValid_key() {
		return valid_key;
	}

	public void setValid_key(boolean valid_key) {
		this.valid_key = valid_key;
	}

	public HGResultsModel getResults() {
		return results;
	}

	public void setResults(HGResultsModel results) {
		this.results = results;
	}

	public Map<String, BigDecimal> getCurrencies() {

		Map<String, BigDecimal> map = new HashMap<>();

		map.put("BRL", new BigDecimal("1.00"));
		map.put("USD", results.getCurrencies().getUSD().getBuy());
		map.put("EUR", results.getCurrencies().getEUR().getBuy());
		map.put("CAD", results.getCurrencies().getCAD().getBuy());
		map.put("BTC", results.getCurrencies().getBTC().getBuy());

		return map;
	}
}

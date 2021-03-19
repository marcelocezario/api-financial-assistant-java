package br.dev.mhc.financialassistantapi.services.hgservice.model;

import org.springframework.beans.factory.annotation.Autowired;

import br.dev.mhc.financialassistantapi.services.CurrencyTypeService;

public class HGCurrenciesModel {

	@Autowired
	CurrencyTypeService currencyService;

	private String source;
	private HGCurrencyModel USD;
	private HGCurrencyModel EUR;
	private HGCurrencyModel CAD;
	private HGCurrencyModel BTC;

	public HGCurrenciesModel() {
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public HGCurrencyModel getUSD() {
		return USD;
	}

	public void setUSD(HGCurrencyModel USD) {
		this.USD = USD;
	}

	public HGCurrencyModel getEUR() {
		return EUR;
	}

	public void setEUR(HGCurrencyModel EUR) {
		this.EUR = EUR;
	}

	public HGCurrencyModel getCAD() {
		return CAD;
	}

	public void setCAD(HGCurrencyModel CAD) {
		this.CAD = CAD;
	}

	public HGCurrencyModel getBTC() {
		return BTC;
	}

	public void setBTC(HGCurrencyModel BTC) {
		this.BTC = BTC;
	}
}

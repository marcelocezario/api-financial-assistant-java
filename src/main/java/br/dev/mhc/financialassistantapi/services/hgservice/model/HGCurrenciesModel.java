package br.dev.mhc.financialassistantapi.services.hgservice.model;

import org.springframework.beans.factory.annotation.Autowired;

import br.dev.mhc.financialassistantapi.services.CurrencyTypeService;

public class HGCurrenciesModel {

	@Autowired
	CurrencyTypeService currencyService;

	private String source;
	private HGCurrencyModel USD;
	private HGCurrencyModel EUR;
	private HGCurrencyModel GBP;
	private HGCurrencyModel ARS;
	private HGCurrencyModel CAD;
	private HGCurrencyModel AUD;
	private HGCurrencyModel JPY;
	private HGCurrencyModel CNY;
	private HGCurrencyModel BTC;

	public HGCurrenciesModel() {
	}

	public CurrencyTypeService getCurrencyService() {
		return currencyService;
	}

	public void setCurrencyService(CurrencyTypeService currencyService) {
		this.currencyService = currencyService;
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

	public void setUSD(HGCurrencyModel uSD) {
		USD = uSD;
	}

	public HGCurrencyModel getEUR() {
		return EUR;
	}

	public void setEUR(HGCurrencyModel eUR) {
		EUR = eUR;
	}

	public HGCurrencyModel getGBP() {
		return GBP;
	}

	public void setGBP(HGCurrencyModel gBP) {
		GBP = gBP;
	}

	public HGCurrencyModel getARS() {
		return ARS;
	}

	public void setARS(HGCurrencyModel aRS) {
		ARS = aRS;
	}

	public HGCurrencyModel getCAD() {
		return CAD;
	}

	public void setCAD(HGCurrencyModel cAD) {
		CAD = cAD;
	}

	public HGCurrencyModel getAUD() {
		return AUD;
	}

	public void setAUD(HGCurrencyModel aUD) {
		AUD = aUD;
	}

	public HGCurrencyModel getJPY() {
		return JPY;
	}

	public void setJPY(HGCurrencyModel jPY) {
		JPY = jPY;
	}

	public HGCurrencyModel getCNY() {
		return CNY;
	}

	public void setCNY(HGCurrencyModel cNY) {
		CNY = cNY;
	}

	public HGCurrencyModel getBTC() {
		return BTC;
	}

	public void setBTC(HGCurrencyModel bTC) {
		BTC = bTC;
	}
}

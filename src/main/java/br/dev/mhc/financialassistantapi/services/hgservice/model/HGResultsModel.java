package br.dev.mhc.financialassistantapi.services.hgservice.model;

public class HGResultsModel {

	private HGCurrenciesModel currencies;
	private HGStocksModel stocks;

	public HGResultsModel() {
	}

	public HGCurrenciesModel getCurrencies() {
		return currencies;
	}

	public void setCurrencies(HGCurrenciesModel currencies) {
		this.currencies = currencies;
	}

	public HGStocksModel getStocks() {
		return stocks;
	}

	public void setStocks(HGStocksModel stocks) {
		this.stocks = stocks;
	}
}

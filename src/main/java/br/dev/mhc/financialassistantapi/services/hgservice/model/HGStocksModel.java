package br.dev.mhc.financialassistantapi.services.hgservice.model;

public class HGStocksModel {

	private HGStockModel ibovespa;
	private HGStockModel nasdaq;

	public HGStocksModel() {
	}

	public HGStockModel getIbovespa() {
		return ibovespa;
	}

	public void setIbovespa(HGStockModel ibovespa) {
		this.ibovespa = ibovespa;
	}

	public HGStockModel getNasdaq() {
		return nasdaq;
	}

	public void setNasdaq(HGStockModel nasdaq) {
		this.nasdaq = nasdaq;
	}
}

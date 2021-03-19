package br.dev.mhc.financialassistantapi.services.hgservice.model;

public class HGStocksModel {

	private HGStockModel IBOVESPA;
	private HGStockModel NASDAQ;
	private HGStockModel CAC;
	private HGStockModel NIKKEI;

	public HGStocksModel() {
	}

	public HGStockModel getIBOVESPA() {
		return IBOVESPA;
	}

	public void setIBOVESPA(HGStockModel iBOVESPA) {
		IBOVESPA = iBOVESPA;
	}

	public HGStockModel getNASDAQ() {
		return NASDAQ;
	}

	public void setNASDAQ(HGStockModel nASDAQ) {
		NASDAQ = nASDAQ;
	}

	public HGStockModel getCAC() {
		return CAC;
	}

	public void setCAC(HGStockModel cAC) {
		CAC = cAC;
	}

	public HGStockModel getNIKKEI() {
		return NIKKEI;
	}

	public void setNIKKEI(HGStockModel nIKKEI) {
		NIKKEI = nIKKEI;
	}
}

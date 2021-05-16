package br.dev.mhc.financialassistantapi.entities.enums;

public enum AccountType {
	
	WALLET(1, "Wallet"),
	BANK_ACCOUNT(2, "Bank account"),
	CREDIT_CARD(3, "Credit card"),
	INVESTMENT_ACCOUNT(4, "Investment account");
	
	private int cod;
	private String description;
	
	private AccountType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescription () {
		return description;
	}
	
	public static AccountType toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (AccountType x : AccountType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + cod);
	}
}
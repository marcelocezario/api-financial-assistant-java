package br.dev.mhc.financialassistantapi.entities.enums;

public enum EntryType {
	
	CREDIT(1, "Credit"),
	DEBIT(2, "Debit");
	
	private int cod;
	private String description;
	
	private EntryType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
		return description;
	}
	
	public static EntryType toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (EntryType x : EntryType.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Invalid id: " + cod);
	}

}
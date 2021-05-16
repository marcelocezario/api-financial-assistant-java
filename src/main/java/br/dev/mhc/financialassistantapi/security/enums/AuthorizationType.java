package br.dev.mhc.financialassistantapi.security.enums;

import br.dev.mhc.financialassistantapi.entities.enums.AccountType;

public enum AuthorizationType {

	ADMIN_ONLY(1, "Admin only"),
	USER_ONLY(2, "User only"),
	USER_OR_ADMIN(3, "User or admin");

	private int cod;
	private String description;

	private AuthorizationType(int cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public int getCod() {
		return cod;
	}

	public String getDescription() {
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
package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;

import br.dev.mhc.financialassistantapi.services.validation.UserInsert;

@UserInsert
public class UserNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String nickname;
	private String email;
	private String password;

	private CurrencyTypeDTO defaultCurrencyType;

	public UserNewDTO() {
	}

	public UserNewDTO(String name, String nickname, String email, String password,
			CurrencyTypeDTO defaultCurrencyType) {
		super();
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.defaultCurrencyType = defaultCurrencyType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CurrencyTypeDTO getDefaultCurrencyType() {
		return defaultCurrencyType;
	}

	public void setDefaultCurrencyType(CurrencyTypeDTO defaultCurrencyType) {
		this.defaultCurrencyType = defaultCurrencyType;
	}
}

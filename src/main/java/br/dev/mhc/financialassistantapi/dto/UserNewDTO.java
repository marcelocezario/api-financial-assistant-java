package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.dev.mhc.financialassistantapi.services.validation.UserInsert;

@UserInsert
public class UserNewDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "The user's name is a required field")
	@Length(max = 80, message = "Maximum number of 80 characters exceeded")
	private String name;

	@Length(max = 80, message = "Maximum number of 80 characters exceeded")
	private String nickname;

	@NotEmpty(message = "The user's email is a required field")
	@Email(message = "Invalid email adress")
	@Column(unique = true)
	private String email;
	
	@NotEmpty(message = "Required field")
	private String password;
	
	private CurrencyTypeDTO defaultCurrencyType;

	public UserNewDTO() {
	}
	
	public UserNewDTO(
			@NotEmpty(message = "Required field") @Length(max = 80, message = "Maximum number of 80 characters exceeded") String name,
			@Length(max = 80, message = "Maximum number of 80 characters exceeded") String nickname,
			@NotEmpty(message = "Required field") @Email(message = "Invalid email adress") String email,
			@NotEmpty(message = "Required field") String password, CurrencyTypeDTO defaultCurrencyType) {
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

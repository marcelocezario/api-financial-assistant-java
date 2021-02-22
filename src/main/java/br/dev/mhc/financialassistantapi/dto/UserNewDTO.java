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

	@NotEmpty(message = "Required field")
	@Length(max = 50, message = "Maximum number of 80 characters exceeded")
	private String nickname;

	@NotEmpty(message = "Required field")
	@Email(message = "Invalid email adress")
	@Column(unique = true)
	private String email;
	
	@NotEmpty(message = "Required field")
	private String password;
	
	public UserNewDTO() {
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
}

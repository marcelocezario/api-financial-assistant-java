package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;

public class UserNewDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nickname;
	private String email;
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

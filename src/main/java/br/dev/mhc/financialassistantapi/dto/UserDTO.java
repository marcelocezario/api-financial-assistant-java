package br.dev.mhc.financialassistantapi.dto;

import java.time.Instant;

import br.dev.mhc.financialassistantapi.entities.User;

public class UserDTO {

	private Long id;
	private String nickname;
	private String email;
	private Instant registrationDate;
	private Instant lastAccess;
	private boolean active;

	public UserDTO() {
	}

	public UserDTO(Long id, String nickname, String email, Instant registrationDate, Instant lastAccess,
			boolean active) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.registrationDate = registrationDate;
		this.lastAccess = lastAccess;
		this.active = active;
	}

	public UserDTO(User obj) {
		this.id = obj.getId();
		this.nickname = obj.getNickname();
		this.email = obj.getEmail();
		this.registrationDate = obj.getRegistrationDate();
		this.lastAccess = obj.getLastAccess();
		this.active = obj.isActive();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Instant getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Instant registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Instant getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Instant lastAccess) {
		this.lastAccess = lastAccess;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}

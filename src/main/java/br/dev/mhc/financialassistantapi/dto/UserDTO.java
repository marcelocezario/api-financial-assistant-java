package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.services.validation.UserUpdate;

@UserUpdate
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Required field")
	@Length(max = 50, message = "Maximum number of 80 characters exceeded")
	private String nickname;

	@NotEmpty(message = "Required field")
	@Email(message = "Invalid email adress")
	@Column(unique = true)
	private String email;

	private Instant registrationMoment;
	private Instant lastAccess;
	private boolean active;

	public UserDTO() {
	}

	public UserDTO(Long id, String nickname, String email, Instant registrationMoment, Instant lastAccess, boolean active) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.registrationMoment = registrationMoment;
		this.lastAccess = lastAccess;
		this.active = active;
	}

	public UserDTO(User obj) {
		this.id = obj.getId();
		this.nickname = obj.getNickname();
		this.email = obj.getEmail();
		this.registrationMoment = obj.getRegistrationMoment();
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

	public Instant getRegistrationMoment() {
		return registrationMoment;
	}

	public void setRegistrationMoment(Instant registrationMoment) {
		this.registrationMoment = registrationMoment;
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

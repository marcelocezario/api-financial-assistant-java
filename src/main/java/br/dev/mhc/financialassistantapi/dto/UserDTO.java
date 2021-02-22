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

	private Instant registrationDate;
	private Instant lastAccess;

	public UserDTO() {
	}

	public UserDTO(Long id, String nickname, String email, Instant registrationDate, Instant lastAccess) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.registrationDate = registrationDate;
		this.lastAccess = lastAccess;
	}

	public UserDTO(User obj) {
		this.id = obj.getId();
		this.nickname = obj.getNickname();
		this.email = obj.getEmail();
		this.registrationDate = obj.getRegistrationDate();
		this.lastAccess = obj.getLastAccess();
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
}

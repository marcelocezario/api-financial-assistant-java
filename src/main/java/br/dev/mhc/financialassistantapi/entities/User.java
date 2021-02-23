package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.mhc.financialassistantapi.dto.UserDTO;
import br.dev.mhc.financialassistantapi.dto.UserNewDTO;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Required field")
	@Length(max = 50, message = "Maximum number of 80 characters exceeded")
	private String nickname;

	@NotEmpty(message = "Required field")
	@Email(message = "Invalid email adress")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "Required field")
	@JsonIgnore
	private String password;

	private Instant registrationMoment;
	private Instant lastAccess;
	private boolean active;

	public User() {
	}

	public User(Long id, String nickname, String email, String password, Instant registrationMoment, Instant lastAccess,
			boolean active) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.registrationMoment = registrationMoment;
		this.lastAccess = lastAccess;
		this.active = active;
	}

	public User(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.nickname = userDTO.getNickname();
		this.email = userDTO.getEmail();
		this.registrationMoment = userDTO.getRegistrationMoment();
		this.lastAccess = userDTO.getLastAccess();
	}

	public User(UserNewDTO userNewDTO) {
		this.nickname = userNewDTO.getNickname();
		this.email = userNewDTO.getEmail();
		this.password = userNewDTO.getPassword();
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withLocale(new Locale("pt", "BR"))
				.withZone(ZoneId.systemDefault());

		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("----------------------------------------------------------");
		builder.append("\n");
		builder.append("Cadastrado realizado com sucesso!\n\n");
		builder.append("Nickname: ");
		builder.append(nickname);
		builder.append("\n\n");
		builder.append("E-mail de cadastro: ");
		builder.append(email);
		builder.append("\n");
		builder.append("Data e hora do cadastro: ");
		builder.append(dtf.format(registrationMoment));
		builder.append("\n");
		builder.append("----------------------------------------------------------");
		builder.append("\n");
		return builder.toString();
	}
}

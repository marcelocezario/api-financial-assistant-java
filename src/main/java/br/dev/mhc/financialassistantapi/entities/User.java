package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.mhc.financialassistantapi.entities.enums.Profile;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String nickname;
	private String email;
	@JsonIgnore
	private String password;
	private Instant registrationMoment;
	private String imageUrl;

	@ManyToOne
	@JoinColumn(name = "default_currency_type_id")
	private CurrencyType defaultCurrencyType;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_profiles")
	private Set<Integer> profiles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Account> accounts = new HashSet<>();

	public User() {
		addProfile(Profile.BASIC_USER);
	}

	public User(Long id, String name, String nickname, String email, String password, Instant registrationMoment,
			String imageUrl, CurrencyType defaultCurrencyType) {
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.registrationMoment = registrationMoment;
		this.imageUrl = imageUrl;
		this.defaultCurrencyType = defaultCurrencyType;
		addProfile(Profile.BASIC_USER);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public CurrencyType getDefaultCurrencyType() {
		return defaultCurrencyType;
	}

	public void setDefaultCurrencyType(CurrencyType defaultCurrencyType) {
		this.defaultCurrencyType = defaultCurrencyType;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Set<Profile> getProfiles() {
		return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
	}

	public void addProfile(Profile profile) {
		profiles.add(profile.getCod());
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
		builder.append("Olá ");
		builder.append(nickname);
		builder.append("!\n\n");
		builder.append("Cadastrado realizado com sucesso!\n\n");
		builder.append("E-mail de cadastro: ");
		builder.append(email);
		builder.append("\n");
		builder.append("Data e hora do cadastro: ");
		builder.append(dtf.format(registrationMoment));
		builder.append("\n");
		builder.append("\n");
		builder.append("O que posso fazer agora?");
		builder.append("\n");
		builder.append("Agora você tem acesso ao app Assitente Financeiro, para lhe ajudar a controlar suas finanças.");
		builder.append("\n");
		builder.append("\n");
		builder.append("---------------------------------------------------------------------------------------------");
		builder.append("\n");
		return builder.toString();
	}
}

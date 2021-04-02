package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.services.validation.UserUpdate;

@UserUpdate
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private String name;
	private String nickname;
	private String email;
	private String imageUrl;
	private Instant creationMoment;
	private Instant lastUpdate;
	private CurrencyTypeDTO defaultCurrencyType;

	private List<Profile> profiles = new ArrayList<>();

	public UserDTO() {
	}

	public UserDTO(String uuid, String name, String nickname, String email, String imageUrl, Instant creationMoment,
			Instant lastUpdate, CurrencyTypeDTO defaultCurrencyType, List<Profile> profiles) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.imageUrl = imageUrl;
		this.creationMoment = creationMoment;
		this.lastUpdate = lastUpdate;
		this.defaultCurrencyType = defaultCurrencyType;
		this.profiles = profiles;
	}

	public UserDTO(User obj) {
		this.uuid = obj.getUuid();
		this.name = obj.getName();
		this.nickname = obj.getNickname();
		this.email = obj.getEmail();
		this.imageUrl = obj.getImageUrl();
		this.creationMoment = obj.getCreationMoment();
		this.lastUpdate = obj.getLastUpdate();
		this.defaultCurrencyType = new CurrencyTypeDTO(obj.getDefaultCurrencyType());
		this.profiles = obj.getProfiles().stream().collect(Collectors.toList());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Instant getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(Instant creationMoment) {
		this.creationMoment = creationMoment;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public CurrencyTypeDTO getDefaultCurrencyType() {
		return defaultCurrencyType;
	}

	public void setDefaultCurrencyType(CurrencyTypeDTO defaultCurrencyType) {
		this.defaultCurrencyType = defaultCurrencyType;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}

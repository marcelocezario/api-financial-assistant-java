package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.time.Instant;

import br.dev.mhc.financialassistantapi.entities.Category;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private String name;
	private String iconUrl;
	private Instant creationMoment;
	private Instant lastUpdate;

	public CategoryDTO() {
	}

	public CategoryDTO(String uuid, String name, String iconUrl, Instant creationMoment, Instant lastUpdate) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.iconUrl = iconUrl;
		this.creationMoment = creationMoment;
		this.lastUpdate = lastUpdate;
	}

	public CategoryDTO(Category obj) {
		this.uuid = obj.getUuid();
		this.name = obj.getName();
		this.iconUrl = obj.getIconUrl();
		this.creationMoment = obj.getCreationMoment();
		this.lastUpdate = obj.getLastUpdate();
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
}

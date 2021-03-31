package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.time.Instant;

import br.dev.mhc.financialassistantapi.entities.Category;

public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String iconUrl;
	private Instant creationMoment;
	private Instant lastUpdate;

	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name, String iconUrl, Instant creationMoment, Instant lastUpdate) {
		super();
		this.id = id;
		this.name = name;
		this.iconUrl = iconUrl;
		this.creationMoment = creationMoment;
		this.lastUpdate = lastUpdate;
	}

	public CategoryDTO(Category obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.iconUrl = obj.getIconUrl();
		this.creationMoment = obj.getCreationMoment();
		this.lastUpdate = obj.getLastUpdate();
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

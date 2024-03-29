package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String name;
	private String iconUrl;
	private boolean defaultForAllUsers;
	private Instant creationMoment;
	private Instant lastUpdate;
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "id.category")
	private Set<EntryCategory> entriesCategories = new HashSet<>();

	public Category() {
		defaultForAllUsers = false;
		active = true;
	}

	public Category(Long id, String uuid, String name, String iconUrl, User user) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.name = name;
		this.iconUrl = iconUrl;
		this.user = user;
		this.defaultForAllUsers = false;
		this.active = true;
	}

	public Category(Long id, String name, String iconUrl, User user, boolean defaultForAllUsers) {
		super();
		this.id = id;
		this.name = name;
		this.iconUrl = iconUrl;
		this.user = user;
		this.defaultForAllUsers = defaultForAllUsers;
	}

	public List<Entry> getEntries() {
		List<Entry> list = new ArrayList<>();
		for (EntryCategory x : entriesCategories) {
			list.add(x.getEntry());
		}
		return list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isDefaultForAllUsers() {
		return defaultForAllUsers;
	}

	public void setDefaultForAllUsers(boolean defaultForAllUsers) {
		this.defaultForAllUsers = defaultForAllUsers;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<EntryCategory> getEntriesCategories() {
		return entriesCategories;
	}

	public void setEntriesCategories(Set<EntryCategory> entriesCategories) {
		this.entriesCategories = entriesCategories;
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
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tb_entry_category")
public class EntryCategory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntryCategoryPK id = new EntryCategoryPK();

	@Positive(message = "Value must be positive")
	private Double value;

	public EntryCategory() {
	}

	public EntryCategory(Entry entry, Category category, Double value) {
		super();
		id.setEntry(entry);
		id.setCategory(category);
		this.value = value;
	}

	public Entry getEntry() {
		return id.getEntry();
	}

	public Category getCategory() {
		return id.getCategory();
	}

	public EntryCategoryPK getId() {
		return id;
	}

	public void setId(EntryCategoryPK id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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
		EntryCategory other = (EntryCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

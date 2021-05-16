package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class EntryCategoryPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "entry_id")
	private Entry entry;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	public Entry getEntry() {
		return entry;
	}
	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((entry == null) ? 0 : entry.hashCode());
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
		EntryCategoryPK other = (EntryCategoryPK) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (entry == null) {
			if (other.entry != null)
				return false;
		} else if (!entry.equals(other.entry))
			return false;
		return true;
	}
}

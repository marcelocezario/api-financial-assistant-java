package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.dev.mhc.financialassistantapi.entities.EntryCategory;

public class EntryCategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CategoryDTO category;
	private BigDecimal value;
	
	public EntryCategoryDTO() {
	}

	public EntryCategoryDTO(CategoryDTO category, BigDecimal value) {
		super();
		this.category = category;
		this.value = value;
	}
	
	public EntryCategoryDTO(EntryCategory obj) {
		this.category = new CategoryDTO(obj.getCategory());
		this.value = obj.getValue();
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}

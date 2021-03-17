package br.dev.mhc.financialassistantapi.dto;

import br.dev.mhc.financialassistantapi.entities.CurrencyType;

public class CurrencyTypeDTO {

	private Long id;
	private String code;
	private String name;
	private String initials;
	private Integer decimalDigits;

	public CurrencyTypeDTO() {
	}

	public CurrencyTypeDTO(Long id, String code, String name, String initials, Integer decimalDigits) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.initials = initials;
		this.decimalDigits = decimalDigits;
	}

	public CurrencyTypeDTO(CurrencyType obj) {
		this.id = obj.getId();
		this.code = obj.getCode();
		this.name = obj.getName();
		this.initials = obj.getInitials();
		this.decimalDigits = obj.getDecimalDigits();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
}

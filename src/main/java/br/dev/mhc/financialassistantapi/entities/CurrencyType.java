package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_currency_use")
public class CurrencyType implements Serializable {

	private static final long serialVersionUID = 1L;

	// dados da ISO4217
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String name;
	private String initials;
	private Integer decimalDigits;

	public CurrencyType() {
	}

	public CurrencyType(Long id, String code, String name, String initials, Integer decimalDigits) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.initials = initials;
		this.decimalDigits = decimalDigits;
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

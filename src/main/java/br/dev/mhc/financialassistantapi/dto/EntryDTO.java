package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Positive;

import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

public class EntryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Instant moment;

	@Positive(message = "Value must be positive")
	private Double value;

	private String description;
	private Instant dueDate;
	private Integer installmentNumber;
	private Integer numberInstallmentsTotal;
	private EntryType entryType;

	private List<CategoryDTO> categories = new ArrayList<>();

	public EntryDTO() {
	}

	public EntryDTO(Long id, Instant moment, @Positive(message = "Value must be positive") Double value,
			String description, Instant dueDate, Integer installmentNumber, Integer numberInstallmentsTotal,
			EntryType entryType, List<CategoryDTO> categories) {
		super();
		this.id = id;
		this.moment = moment;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
		this.entryType = entryType;
		this.categories = categories;
	}

	public EntryDTO(Entry obj) {
		this.id = obj.getId();
		this.moment = obj.getMoment();
		this.value = obj.getValue();
		this.description = obj.getDescription();
		this.dueDate = obj.getDueDate();
		this.installmentNumber = obj.getInstallmentNumber();
		this.numberInstallmentsTotal = obj.getNumberInstallmentsTotal();
		this.entryType = obj.getEntryType();
		this.categories = obj.getCategories().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instant getDueDate() {
		return dueDate;
	}

	public void setDueDate(Instant dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public Integer getNumberInstallmentsTotal() {
		return numberInstallmentsTotal;
	}

	public void setNumberInstallmentsTotal(Integer numberInstallmentsTotal) {
		this.numberInstallmentsTotal = numberInstallmentsTotal;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}

package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

public class EntryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private BigDecimal value;
	private String description;
	private Instant dueDate;
	private Instant paymentMoment;
	private Integer installmentNumber;
	private Integer numberInstallmentsTotal;
	private EntryType entryType;
	private Instant creationMoment;
	private Instant lastUpdate;

	private List<EntryCategoryDTO> categories = new ArrayList<>();

	public EntryDTO() {
	}

	public EntryDTO(Long id, BigDecimal value, String description, Instant dueDate, Instant paymentMoment,
			Integer installmentNumber, Integer numberInstallmentsTotal, EntryType entryType, Instant creationMoment,
			Instant lastUpdate, List<EntryCategoryDTO> categories) {
		super();
		this.id = id;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentMoment = paymentMoment;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
		this.entryType = entryType;
		this.creationMoment = creationMoment;
		this.lastUpdate = lastUpdate;
		this.categories = categories;
	}

	public EntryDTO(Entry obj) {
		this.id = obj.getId();
		this.value = obj.getValue();
		this.description = obj.getDescription();
		this.dueDate = obj.getDueDate();
		this.paymentMoment = obj.getPaymentMoment();
		this.installmentNumber = obj.getInstallmentNumber();
		this.numberInstallmentsTotal = obj.getNumberInstallmentsTotal();
		this.entryType = obj.getEntryType();
		this.categories = obj.getCategories().stream().map(x -> new EntryCategoryDTO(x)).collect(Collectors.toList());
		this.creationMoment = obj.getCreationMoment();
		this.lastUpdate = obj.getLastUpdate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
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

	public Instant getPaymentMoment() {
		return paymentMoment;
	}

	public void setPaymentMoment(Instant paymentMoment) {
		this.paymentMoment = paymentMoment;
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

	public List<EntryCategoryDTO> getCategories() {
		return categories;
	}
}

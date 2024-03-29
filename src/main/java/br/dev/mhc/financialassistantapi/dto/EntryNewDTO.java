package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

public class EntryNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uuid;
	private BigDecimal value;
	private String description;
	private Instant dueDate;
	private Instant paymentMoment;
	private Integer installmentNumber;
	private Integer numberInstallmentsTotal;
	private EntryType entryType;
	private AccountDTO account;

	private List<EntryCategoryDTO> categories = new ArrayList<>();

	public EntryNewDTO() {
	}

	public EntryNewDTO(String uuid, BigDecimal value, String description, Instant dueDate, Instant paymentMoment,
			Integer installmentNumber, Integer numberInstallmentsTotal, EntryType entryType, AccountDTO account,
			List<EntryCategoryDTO> entriesCategories) {
		super();
		this.uuid = uuid;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentMoment = paymentMoment;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
		this.entryType = entryType;
		this.account = account;
		this.categories = entriesCategories;
	}

	public EntryNewDTO(Entry obj) {
		this.uuid = obj.getUuid();
		this.value = obj.getValue();
		this.description = obj.getDescription();
		this.dueDate = obj.getDueDate();
		this.paymentMoment = obj.getPaymentMoment();
		this.installmentNumber = obj.getInstallmentNumber();
		this.numberInstallmentsTotal = obj.getNumberInstallmentsTotal();
		this.entryType = obj.getEntryType();
		this.account = new AccountDTO(obj.getAccount());
		this.categories = obj.getCategories().stream().map(x -> new EntryCategoryDTO(x)).collect(Collectors.toList());
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
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

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}

	public List<EntryCategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<EntryCategoryDTO> categories) {
		this.categories = categories;
	}
}

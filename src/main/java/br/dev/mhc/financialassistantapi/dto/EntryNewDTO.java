package br.dev.mhc.financialassistantapi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

@br.dev.mhc.financialassistantapi.services.validation.Entry
public class EntryNewDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Instant criationMoment;

	@NotNull
	@PositiveOrZero
	private BigDecimal value;
	private String description;
	private Instant dueDate;
	private Instant paymentMoment;

	@NotNull
	@PositiveOrZero
	private Integer installmentNumber;

	@NotNull
	@PositiveOrZero
	private Integer numberInstallmentsTotal;

	@NotNull
	private EntryType entryType;
	
	private AccountDTO account;

//	private List<CategoryDTO> categories = new ArrayList<>();

	public EntryNewDTO() {
	}

	public EntryNewDTO(Long id, Instant criationMoment, BigDecimal value, String description, Instant dueDate,
			Instant paymentMoment, Integer installmentNumber, Integer numberInstallmentsTotal, EntryType entryType,
			AccountDTO account, List<CategoryDTO> categories) {
		super();
		this.id = id;
		this.criationMoment = criationMoment;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentMoment = paymentMoment;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
		this.entryType = entryType;
		this.account = account;
//		this.categories = categories;
	}

	public EntryNewDTO(Entry obj) {
		this.id = obj.getId();
		this.criationMoment = obj.getCriationMoment();
		this.value = obj.getValue();
		this.description = obj.getDescription();
		this.dueDate = obj.getDueDate();
		this.paymentMoment = obj.getPaymentMoment();
		this.installmentNumber = obj.getInstallmentNumber();
		this.numberInstallmentsTotal = obj.getNumberInstallmentsTotal();
		this.entryType = obj.getEntryType();
		this.account = new AccountDTO(obj.getAccount());
//		this.categories = obj.getCategories().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getCriationMoment() {
		return criationMoment;
	}

	public void setCriationMoment(Instant criationMoment) {
		this.criationMoment = criationMoment;
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

//	public List<CategoryDTO> getCategories() {
//		return categories;
//	}
}

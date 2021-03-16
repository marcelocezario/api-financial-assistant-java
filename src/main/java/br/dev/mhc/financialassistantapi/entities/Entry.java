package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

@Entity
@Table(name = "tb_entry")
public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Instant criationMoment;
	
	@NotNull
	@PositiveOrZero
	private BigDecimal value;
	
	private String description;

	@NotNull
	private Instant dueDate;

	private Instant paymentMoment;
	
	@NotNull
	@PositiveOrZero
	private Integer installmentNumber;

	@NotNull
	@PositiveOrZero
	private Integer numberInstallmentsTotal;
	
	@NotNull
	private Integer entryType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "id.entry")
	private Set<EntryCategory> entriesCategories = new HashSet<>();
	
	public Entry() {
	}

	public Entry(Long id, Instant criationMoment, BigDecimal value, String description, Instant dueDate,
			Instant paymentMoment, Integer installmentNumber, Integer numberInstallmentsTotal, EntryType entryType,
			Account account, User user, Set<EntryCategory> entriesCategories) {
		super();
		this.id = id;
		this.criationMoment = criationMoment;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentMoment = paymentMoment;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
		this.entryType = entryType.getCod();
		this.account = account;
		this.user = user;
		this.entriesCategories = entriesCategories;
	}

	public List<Category> getCategories() {
		List<Category> list = new ArrayList<>();
		for (EntryCategory x : entriesCategories) {
			list.add(x.getCategory());
		}
		return list;
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
		return EntryType.toEnum(entryType);
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType.getCod();
	}

	public void setEntryType(Integer entryType) {
		this.entryType = entryType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public void setCategories(Set<EntryCategory> entriesCategories) {
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
		Entry other = (Entry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

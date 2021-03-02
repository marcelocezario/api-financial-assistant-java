package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "tb_entry")
public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Entry moment is a required field")
	private Instant moment;

	@NotEmpty(message = "Entry value is a required field")
	@Positive(message = "Value must be positive")
	private Double value;
	private String description;

	@NotEmpty(message = "Due date is a required field")
	private Instant dueDate;

	@NotEmpty(message = "Installment number is a required field")
	private Integer installmentNumber;

	@NotEmpty(message = "Number installments total is a required field")
	private Integer numberInstallmentsTotal;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@OneToMany(mappedBy = "id.entry")
	private Set<EntryCategory> entriesCategories = new HashSet<>();

	public Entry() {
	}

	public Entry(Long id, Instant moment, Double value, String description, Instant dueDate, Integer installmentNumber,
			Integer numberInstallmentsTotal) {
		super();
		this.id = id;
		this.moment = moment;
		this.value = value;
		this.description = description;
		this.dueDate = dueDate;
		this.installmentNumber = installmentNumber;
		this.numberInstallmentsTotal = numberInstallmentsTotal;
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

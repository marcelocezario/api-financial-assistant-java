package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.dev.mhc.financialassistantapi.entities.enums.AccountType;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;

@Entity
@Table(name = "tb_account")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "The account's name is a required field")
	@Length(max = 50, message = "Maximum number of 50 characters exceeded")
	private String name;
	private Double balance;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull(message = "Entrytype cannot be null")
	private Integer accountType;

	@OneToMany(mappedBy = "account")
	private Set<Entry> entries = new HashSet<>();

	public Account() {
		balance = 0.0;
	}

	public Account(Long id, String name, Double balance, AccountType accountType, User user) {
		super();
		this.id = id;
		this.name = name;
		if (balance == null) {
			this.balance = 0.0;
		} else {
			this.balance = balance;
		}
		this.accountType = accountType.getCod();
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getBalance() {
		return balance;
	}

	public void increaseBalance(Double value) {
		this.balance += value;
	}

	public void decreaseBalance(Double value) {
		this.balance -= value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccountType getAccountType() {
		return AccountType.toEnum(accountType);
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType.getCod();
	}

	public Set<Entry> getEntries() {
		return entries;
	}

	public void addEntry(Entry entry) {
		if (entry.getEntryType() == EntryType.CREDIT) {
			increaseBalance(entry.getValue());
		} else {
			decreaseBalance(entry.getValue());
		}
		entries.add(entry);
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

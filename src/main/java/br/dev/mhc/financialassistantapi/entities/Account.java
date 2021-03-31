package br.dev.mhc.financialassistantapi.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
	private String name;
	private BigDecimal balance;
	private Integer accountType;

	@ManyToOne
	@JoinColumn(name = "currency_type_id")
	private CurrencyType currencyType;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "account")
	private Set<Entry> entries = new HashSet<>();

	public Account() {
		balance = new BigDecimal("0");
	}

	public Account(Long id, String name, BigDecimal balance, AccountType accountType, CurrencyType currencyType,
			User user) {
		super();
		this.id = id;
		this.name = name;
		if (balance == null) {
			this.balance = new BigDecimal("0");
		} else {
			this.balance = balance;
		}
		this.accountType = accountType.getCod();
		this.currencyType = currencyType;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void increaseBalance(BigDecimal value) {
		this.balance = this.balance.add(value);
	}

	public void decreaseBalance(BigDecimal value) {
		this.balance = this.balance.subtract(value);
	}

	public AccountType getAccountType() {
		return AccountType.toEnum(accountType);
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType.getCod();
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.AccountDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.accounts.InvestmentAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.Wallet;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;
import br.dev.mhc.financialassistantapi.repositories.AccountRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;
import br.dev.mhc.financialassistantapi.services.interfaces.CrudInterface;

@Service
public class AccountService implements CrudInterface<Account, Long> {

	@Autowired
	private DefaultService defaultService;

	@Autowired
	private AccountRepository repository;

	@Autowired
	private UserService userService;

	@Autowired
	private EntryService entryService;

	@Autowired
	private CurrencyTypeService currencyService;

	@Transactional
	@Override
	public Account insert(Account obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		obj.setId(null);
		if (obj.getUuid() == null)
			obj.setUuid(UUID.randomUUID().toString());
		obj.setCreationMoment(Instant.now());
		obj.setUser(userService.findById(userSS.getId()));
		obj = repository.save(obj);
		return obj;
	}

	@Transactional
	@Override
	public Account update(Account obj) {
		Account newObj = findById(obj.getId());
		AuthService.validatesUserAuthorization(newObj.getUser().getId(), AuthorizationType.USER_ONLY);
		newObj.setName(obj.getName());
		newObj.setLastUpdate(Instant.now());
		switch (obj.getAccountType()) {
		case WALLET:
			break;
		case BANK_ACCOUNT:
			((BankAccount) newObj).setBankInterestRate(((BankAccount) obj).getBankInterestRate());
			((BankAccount) newObj).setLimitValue(((BankAccount) obj).getLimitValueBankAccount());
			break;
		case CREDIT_CARD:
			((CreditCard) newObj).setClosingDay(((CreditCard) obj).getClosingDay());
			((CreditCard) newObj).setDueDay(((CreditCard) obj).getDueDay());
			((CreditCard) newObj).setLimitValueCard(((CreditCard) obj).getLimitValueCard());
			break;
		case INVESTMENT_ACCOUNT:
			break;
		}
		return repository.save(newObj);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		Account account = findById(id);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		repository.delete(account);
	}

	@Override
	public Account findById(Long id) {
		Optional<Account> obj = repository.findById(id);
		Account account = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Uuid: " + id + ", Type: " + Account.class.getName()));
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		return account;
	}

	@Override
	public Account findByUuid(String uuid) {
		Optional<Account> obj = repository.findByUuid(uuid);
		Account account = obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + uuid + ", Type: " + Account.class.getName()));
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		return account;
	}

	@Override
	public List<Account> findAll() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		return repository.findAll();
	}

	@Override
	public Page<Account> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public List<Account> findByUser() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		return repository.findByUser(userService.findById(userSS.getId()));
	}

	public Entry adjustBalance(Account account, BigDecimal newBalance) {
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		BigDecimal valueEntry = newBalance.subtract(account.getBalance());
		if (valueEntry.compareTo(BigDecimal.ZERO) < 0) {
			return entryService.createAdjustEntry(account, valueEntry.abs(), EntryType.DEBIT);
		} else {
			return entryService.createAdjustEntry(account, valueEntry.abs(), EntryType.CREDIT);
		}
	}

	@Transactional
	public Account increaseBalanceAccount(Long idAccount, BigDecimal valueEntry) {
		Account account = findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		account.increaseBalance(valueEntry);
		return update(account);
	}

	@Transactional
	public Account decreaseBalanceAccount(Long idAccount, BigDecimal valueEntry) {
		Account account = findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		account.decreaseBalance(valueEntry);
		return update(account);
	}

	public Account fromDTO(AccountDTO objDTO) {
		CurrencyType currency;
		if (objDTO.getCurrencyType() == null) {
			currency = defaultService.defaultCurrency();
		} else {
			currency = currencyService.fromDTO(objDTO.getCurrencyType());
		}
		switch (objDTO.getAccountType()) {
		case WALLET:
			return new Wallet(null, objDTO.getUuid(), objDTO.getName(), objDTO.getBalance(), currency, null);
		case BANK_ACCOUNT:
			return new BankAccount(null, objDTO.getUuid(), objDTO.getName(), objDTO.getBalance(), currency,
					objDTO.getBankInterestRate(), objDTO.getLimitValueBankAccount(), null);
		case CREDIT_CARD:
			return new CreditCard(null, objDTO.getUuid(), objDTO.getName(), objDTO.getBalance(), currency,
					objDTO.getClosingDay(), objDTO.getDueDay(), objDTO.getLimitValueCard(), null);
		case INVESTMENT_ACCOUNT:
			return new InvestmentAccount(null, objDTO.getUuid(), objDTO.getName(), objDTO.getBalance(), currency, null);
		}
		return null;
	}
}

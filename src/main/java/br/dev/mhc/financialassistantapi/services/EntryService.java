package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.EntryCategoryDTO;
import br.dev.mhc.financialassistantapi.dto.EntryDTO;
import br.dev.mhc.financialassistantapi.dto.EntryNewDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.EntryCategory;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;
import br.dev.mhc.financialassistantapi.repositories.EntryCategoryRepository;
import br.dev.mhc.financialassistantapi.repositories.EntryRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class EntryService implements CrudInterface<Entry, Long> {

	@Autowired
	private EntryRepository repository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EntryCategoryRepository entryCategoryRepository;

	@Transactional
	@Override
	public Entry insert(Entry obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		obj.setUser(userService.findById(userSS.getId()));
		Long idUserAccount = (obj.getAccount() == null) ? userSS.getId() : obj.getAccount().getUser().getId();
		AuthService.validatesUserAuthorization(idUserAccount, AuthorizationType.USER_ONLY);

		if (obj.getAccount() != null && obj.getPaymentMoment() != null) {
			if (obj.getEntryType() == EntryType.CREDIT) {
				obj.setAccount(accountService.increaseBalanceAccount(obj.getAccount().getId(), obj.getValue()));
			} else {
				obj.setAccount(accountService.decreaseBalanceAccount(obj.getAccount().getId(), obj.getValue()));
			}
		}
		obj.setId(null);
		obj.setCreationMoment(Instant.now());
		obj = repository.save(obj);
		entryCategoryRepository.saveAll(obj.getCategories());
		return obj;
	}

	@Transactional
	@Override
	public Entry update(Entry obj) {
		Entry newObj = findById(obj.getId());
		AuthService.validatesUserAuthorization(newObj.getAccount().getUser().getId(), AuthorizationType.USER_ONLY);
		if (newObj.getAccount() != null && newObj.getPaymentMoment() != null) {
			if (newObj.getEntryType() == EntryType.CREDIT) {
				accountService.decreaseBalanceAccount(newObj.getAccount().getId(), newObj.getValue());
			} else {
				accountService.increaseBalanceAccount(newObj.getAccount().getId(), newObj.getValue());
			}
		}
		if (obj.getAccount() != null && obj.getPaymentMoment() != null) {
			if (obj.getEntryType() == EntryType.CREDIT) {
				accountService.increaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			} else {
				accountService.decreaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			}
		}
		newObj.setValue(obj.getValue());
		newObj.setDescription(obj.getDescription());
		newObj.setDueDate(obj.getDueDate());
		newObj.setPaymentMoment(obj.getPaymentMoment());
		newObj.setInstallmentNumber(obj.getInstallmentNumber());
		newObj.setNumberInstallmentsTotal(obj.getNumberInstallmentsTotal());
		newObj.setEntryType(obj.getEntryType());
		newObj.setAccount(obj.getAccount());
		newObj.setLastUpdate(Instant.now());
		return repository.save(newObj);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Entry findById(Long id) {
		Optional<Entry> obj = repository.findById(id);
		Entry entry = obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Entry.class.getName()));
		AuthService.validatesUserAuthorization(entry.getUser().getId(), AuthorizationType.USER_ONLY);
		return entry;
	}

	@Override
	public List<Entry> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Entry> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Entry> findByAccount(Long idAccount) {
		Account account = accountService.findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		return repository.findByAccountOrderByPaymentMomentDesc(account);
	}

	public List<Entry> findByUserWithoutAccount() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		User user = userService.findById(userSS.getId());
		return repository.findByUserAndAccountIsNullOrderByDueDateAsc(user);
	}

	public List<Entry> findUnpaidByUser() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		User user = userService.findById(userSS.getId());
		return repository.findByUserAndPaymentMomentIsNullOrderByDueDateAsc(user);
	}

	public Page<Entry> findPageByAccount(Long idAccount, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		Account account = accountService.findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByAccount(account, pageRequest);
	}

	public Page<Entry> findPageByUserWithoutAccount(Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		User user = userService.findById(userSS.getId());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByUserAndAccountIsNull(user, pageRequest);
	}

	public Page<Entry> findPageUnpaidByUser(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);
		User user = userService.findById(userSS.getId());
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByUserAndPaymentMomentIsNull(user, pageRequest);
	}

	public Entry fromDTO(EntryDTO objDTO) {
		Entry entry = new Entry(objDTO.getId(), objDTO.getValue(), objDTO.getDescription(), objDTO.getDueDate(),
				objDTO.getPaymentMoment(), objDTO.getInstallmentNumber(), objDTO.getNumberInstallmentsTotal(),
				objDTO.getEntryType(), null, null);
		return entry;
	}

	public Entry fromDTO(EntryNewDTO objDTO) {
		Account account = (objDTO.getAccount() == null) ? null : accountService.findById(objDTO.getAccount().getId());

		Entry entry = new Entry(objDTO.getId(), objDTO.getValue(), objDTO.getDescription(), objDTO.getDueDate(),
				objDTO.getPaymentMoment(), objDTO.getInstallmentNumber(), objDTO.getNumberInstallmentsTotal(),
				objDTO.getEntryType(), account, null);

		for (EntryCategoryDTO x : objDTO.getCategories()) {
			entry.addCategory(new EntryCategory(entry, categoryService.fromDTO(x.getCategory()), x.getValue()));
		}
		return entry;
	}

	public Entry createAdjustEntry(Account account, BigDecimal valueEntry, EntryType entryType) {
		Entry entry = new Entry(null, valueEntry, "Ajuste de saldo", Instant.now(), Instant.now(), 1, 1, entryType,
				account, null);
		return insert(entry);
	}
}

package br.dev.mhc.financialassistantapi.services;

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
public class EntryService {

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

	/*
	 * CREATE
	 */

	@Transactional
	public Entry insert(Entry obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		obj.setUser(userService.findById(userSS.getId()));
		Long idUserAccount = (obj.getAccount() == null) ? userSS.getId() : obj.getAccount().getUser().getId();
		AuthService.validatesUserAuthorization(idUserAccount, AuthorizationType.USER_ONLY);

		if (obj.getAccount() != null && obj.getPaymentMoment() != null) {
			if (obj.getEntryType() == EntryType.CREDIT) {
				accountService.increaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			} else {
				accountService.decreaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			}
		}
		obj.setId(null);
		obj.setCriationMoment(Instant.now());
		obj = repository.save(obj);
		entryCategoryRepository.saveAll(obj.getCategories());
		return obj;
	}

	/*
	 * READ
	 */

	public Entry findById(Long id) {
		Optional<Entry> obj = repository.findById(id);
		Entry entry = obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Entry.class.getName()));
		AuthService.validatesUserAuthorization(entry.getUser().getId(), AuthorizationType.USER_ONLY);
		return entry;
	}

	/*
	 * READ LISTS
	 */

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

	/*
	 * READ PAGE
	 */
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

	/*
	 * UPDATE
	 */

	@Transactional
	public Entry update(Entry obj) {
		Entry objAux = findById(obj.getId());
		AuthService.validatesUserAuthorization(objAux.getAccount().getUser().getId(), AuthorizationType.USER_ONLY);

		if (objAux.getAccount() != null && objAux.getPaymentMoment() != null) {
			if (objAux.getEntryType() == EntryType.CREDIT) {
				accountService.decreaseBalanceAccount(objAux.getAccount().getId(), objAux.getValue());
			} else {
				accountService.increaseBalanceAccount(objAux.getAccount().getId(), objAux.getValue());
			}
		}

		if (obj.getAccount() != null && obj.getPaymentMoment() != null) {
			if (obj.getEntryType() == EntryType.CREDIT) {
				accountService.increaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			} else {
				accountService.decreaseBalanceAccount(obj.getAccount().getId(), obj.getValue());
			}
		}
		updateData(objAux, obj);
		return repository.save(objAux);
	}

	/*
	 * UTILS
	 */

	private void updateData(Entry newObj, Entry obj) {
		newObj.setValue(obj.getValue());
		newObj.setDescription(obj.getDescription());
		newObj.setDueDate(obj.getDueDate());
		newObj.setPaymentMoment(obj.getPaymentMoment());
		newObj.setInstallmentNumber(obj.getInstallmentNumber());
		newObj.setNumberInstallmentsTotal(obj.getNumberInstallmentsTotal());
		newObj.setEntryType(obj.getEntryType());
		newObj.setAccount(obj.getAccount());
	}

	public Entry fromDTO(EntryDTO objDTO) {
		Entry entry = new Entry(objDTO.getId(), objDTO.getCriationMoment(), objDTO.getValue(), objDTO.getDescription(),
				objDTO.getDueDate(), objDTO.getPaymentMoment(), objDTO.getInstallmentNumber(),
				objDTO.getNumberInstallmentsTotal(), objDTO.getEntryType(), null, null);
		return entry;
	}

	public Entry fromDTO(EntryNewDTO objDTO) {
		Account account = (objDTO.getAccount() == null) ? null : accountService.findById(objDTO.getAccount().getId());

		Entry entry = new Entry(objDTO.getId(), objDTO.getCriationMoment(), objDTO.getValue(), objDTO.getDescription(),
				objDTO.getDueDate(), objDTO.getPaymentMoment(), objDTO.getInstallmentNumber(),
				objDTO.getNumberInstallmentsTotal(), objDTO.getEntryType(), account, null);

		for (EntryCategoryDTO x : objDTO.getCategories()) {
			entry.addCategory(new EntryCategory(entry, categoryService.fromDTO(x.getCategory()), x.getValue()));
		}
		return entry;
	}
}

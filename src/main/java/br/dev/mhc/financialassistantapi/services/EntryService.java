package br.dev.mhc.financialassistantapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.EntryDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.repositories.EntryRepository;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class EntryService {

	@Autowired
	private EntryRepository repository;

	@Autowired
	private AccountService accountService;

	@Transactional
	public Entry insert(Entry obj) {
		AuthService.validatesUserAuthorization(obj.getAccount().getUser().getId(), AuthorizationType.USER_ONLY);
		obj.setId(null);
		obj = repository.save(obj);
		return obj;
	}

	/*
	 * En: For now not using this method, there is no need to search for Entry only
	 * by Id without Account Pt: Por enquanto não utilizar esse método, não há
	 * necessidade de buscar Entry apenas por Id sem Account
	 * 
	 * private Entry findById(Long id) { Optional<Entry> obj =
	 * repository.findById(id); Entry entry = obj.orElseThrow( () -> new
	 * ObjectNotFoundException("Object not found! Id: " + id + ", Type: " +
	 * Entry.class.getName()));
	 * AuthService.validatesUserAuthorization(entry.getAccount().getUser().getId(),
	 * AuthorizationType.USER_ONLY); return entry; }
	 */

	public Entry findByIdAndIdAccount(Long idEntry, Long idAccount) {
		Account account = accountService.findById(idAccount);
		Entry entry = repository.findByIdAndAccount(idEntry, account);
		if (entry == null) {
			throw new ObjectNotFoundException("Object not found! Id: " + idEntry + ", Type: " + Entry.class.getName());
		}

		AuthService.validatesUserAuthorization(entry.getAccount().getUser().getId(), AuthorizationType.USER_ONLY);
		return entry;
	}

	public List<Entry> findByAccount(Long idAccount) {
		Account account = accountService.findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);

		return repository.findByAccountOrderByMomentDesc(account);
	}

	public Page<Entry> findPageByAccount(Long idAccount, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		Account account = accountService.findById(idAccount);
		AuthService.validatesUserAuthorization(account.getUser().getId(), AuthorizationType.USER_ONLY);

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findByAccount(account, pageRequest);
	}

	@Transactional
	public Entry update(Entry obj, Long idAccount) {
		Entry newObj = findByIdAndIdAccount(obj.getId(), idAccount);
		AuthService.validatesUserAuthorization(newObj.getAccount().getUser().getId(), AuthorizationType.USER_ONLY);
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	private void updateData(Entry newObj, Entry obj) {
		newObj.setMoment(obj.getMoment());
		newObj.setValue(obj.getValue());
		newObj.setEntryType(obj.getEntryType());
		newObj.setDescription(obj.getDescription());
		newObj.setDueDate(obj.getDueDate());
		newObj.setInstallmentNumber(obj.getInstallmentNumber());
		newObj.setNumberInstallmentsTotal(obj.getNumberInstallmentsTotal());
	}

	public Entry fromDTO(EntryDTO objDTO) {
		Entry entry = new Entry(objDTO.getId(), objDTO.getMoment(), objDTO.getValue(), objDTO.getDescription(),
				objDTO.getDueDate(), objDTO.getInstallmentNumber(), objDTO.getNumberInstallmentsTotal(),
				objDTO.getEntryType(), null);
		return entry;
	}
}

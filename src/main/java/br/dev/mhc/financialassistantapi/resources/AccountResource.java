package br.dev.mhc.financialassistantapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.dev.mhc.financialassistantapi.dto.AccountBalanceDTO;
import br.dev.mhc.financialassistantapi.dto.AccountDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.services.AccountService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {

	@Autowired
	private AccountService service;

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody AccountDTO objDTO) {
		Account obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(obj.getUuid())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PutMapping(value = "/{uuid}")
	public ResponseEntity<Void> update(@Valid @RequestBody AccountDTO objDTO, @PathVariable String uuid) {
		Account obj = service.fromDTO(objDTO);
		obj.setId(service.findByUuid(uuid).getId());
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping
	public ResponseEntity<List<AccountDTO>> findAllByUser() {
		List<Account> list = service.findByUser();
		List<AccountDTO> listDTO = list.stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/{uuid}")
	public ResponseEntity<AccountDTO> findByUuid(@PathVariable String uuid) {
		Account obj = service.findByUuid(uuid);
		return ResponseEntity.ok().body(new AccountDTO(obj));
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PostMapping(value = "/{uuid}/adjustBalance")
	public ResponseEntity<Void> adjustBalance(@Valid @RequestBody AccountBalanceDTO objDTO, @PathVariable String uuid) {
		Account account = service.findByUuid(uuid);
		if (account.getBalance() == objDTO.getBalance()) {
			return ResponseEntity.noContent().build();
		}
		Entry entryAdjust = service.adjustBalance(account, objDTO.getBalance());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(entryAdjust.getUuid())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
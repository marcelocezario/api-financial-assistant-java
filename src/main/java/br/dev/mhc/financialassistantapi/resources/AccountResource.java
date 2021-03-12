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

import br.dev.mhc.financialassistantapi.dto.AccountDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.services.AccountService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {

	@Autowired
	private AccountService accountService;

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody AccountDTO objDTO) {
		Account obj = accountService.fromDTO(objDTO);
		obj = accountService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping
	public ResponseEntity<List<AccountDTO>> findAllByUser() {
		List<Account> list = accountService.findByUser();
		List<AccountDTO> listDTO = list.stream().map(x -> new AccountDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AccountDTO> findById(@PathVariable Long id) {
		Account obj = accountService.findById(id);
		return ResponseEntity.ok().body(new AccountDTO(obj));
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AccountDTO objDTO, @PathVariable Long id) {
		Account obj = accountService.fromDTO(objDTO);
		obj.setId(id);
		obj = accountService.update(obj);
		return ResponseEntity.noContent().build();
	}
}

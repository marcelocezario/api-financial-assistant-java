package br.dev.mhc.financialassistantapi.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.dev.mhc.financialassistantapi.dto.EntryDTO;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.services.AccountService;
import br.dev.mhc.financialassistantapi.services.EntryService;

@RestController
@RequestMapping(value = "/accounts/{idAccount}/entries")
public class EntryResource {

	@Autowired
	private EntryService service;

	@Autowired
	private AccountService accountService;

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PostMapping
	public ResponseEntity<Void> insertEntry(@PathVariable Long idAccount, @Valid @RequestBody EntryDTO objDTO) {
		Entry obj = service.fromDTO(objDTO);
		obj.setAccount(accountService.findById(idAccount));
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping
	public ResponseEntity<List<EntryDTO>> findAllEntriesByAccount(@PathVariable Long idAccount) {
		List<Entry> list = service.findByAccount(idAccount);
		List<EntryDTO> listDTO = list.stream().map(x -> new EntryDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<EntryDTO>> findAllEntriesPageByAccount(@PathVariable Long idAccount,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Entry> list = service.findPageByAccount(idAccount, page, linesPerPage, orderBy, direction);
		Page<EntryDTO> listDTO = list.map(x -> new EntryDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/{idEntry}")
	public ResponseEntity<EntryDTO> findEntryByIdAndAccount(@PathVariable Long idEntry, @PathVariable Long idAccount) {
		Entry obj = service.findByIdAndIdAccount(idEntry, idAccount);
		return ResponseEntity.ok().body(new EntryDTO(obj));
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody EntryDTO objDTO, @PathVariable Long idAccount,
			@PathVariable Long id) {
		Entry obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj, idAccount);
		return ResponseEntity.noContent().build();
	}
}

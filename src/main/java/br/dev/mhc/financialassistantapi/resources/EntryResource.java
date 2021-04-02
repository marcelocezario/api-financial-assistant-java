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
import br.dev.mhc.financialassistantapi.dto.EntryNewDTO;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.services.AccountService;
import br.dev.mhc.financialassistantapi.services.EntryService;

@RestController
@RequestMapping(value = "/entries")
public class EntryResource {

	@Autowired
	private EntryService service;

	@Autowired
	private AccountService accountService;

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody EntryNewDTO objDTO) {
		Entry obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(obj.getUuid())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/{uuid}")
	public ResponseEntity<EntryDTO> findById(@PathVariable String uuid) {
		Entry obj = service.findByUuid(uuid);
		return ResponseEntity.ok().body(new EntryDTO(obj));
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@PutMapping(value = "/{uuidEntry}")
	public ResponseEntity<Void> update(@Valid @RequestBody EntryDTO objDTO, @PathVariable String uuidEntry) {
		Entry obj = service.fromDTO(objDTO);
		obj.setId(service.findByUuid(uuidEntry).getId());
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/account/{uuidAccount}")
	public ResponseEntity<List<EntryDTO>> findByAccount(@PathVariable String uuidAccount) {
		List<Entry> list = service.findByAccount(accountService.findByUuid(uuidAccount));
		List<EntryDTO> listDTO = list.stream().map(x -> new EntryDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/account/{uuidAccount}/page")
	public ResponseEntity<Page<EntryDTO>> findPageByAccount(@PathVariable String uuidAccount,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "paymentMoment") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Entry> list = service.findPageByAccount(accountService.findByUuid(uuidAccount), page, linesPerPage,
				orderBy, direction);
		Page<EntryDTO> listDTO = list.map(x -> new EntryDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/account/without")
	public ResponseEntity<List<EntryDTO>> findByUserWithoutAccount() {
		List<Entry> list = service.findByUserWithoutAccount();
		List<EntryDTO> listDTO = list.stream().map(x -> new EntryDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/account/without/page")
	public ResponseEntity<Page<EntryDTO>> findPageByUserWithoutAccount(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "dueDate") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Entry> list = service.findPageByUserWithoutAccount(page, linesPerPage, orderBy, direction);
		Page<EntryDTO> listDTO = list.map(x -> new EntryDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/unpaid")
	public ResponseEntity<List<EntryDTO>> findUnpaidByUser() {
		List<Entry> list = service.findUnpaidByUser();
		List<EntryDTO> listDTO = list.stream().map(x -> new EntryDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping(value = "/unpaid/page")
	public ResponseEntity<Page<EntryDTO>> findPageUnpaidByUser(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "dueDate") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Entry> list = service.findPageUnpaidByUser(page, linesPerPage, orderBy, direction);
		Page<EntryDTO> listDTO = list.map(x -> new EntryDTO(x));
		return ResponseEntity.ok().body(listDTO);
	}
}

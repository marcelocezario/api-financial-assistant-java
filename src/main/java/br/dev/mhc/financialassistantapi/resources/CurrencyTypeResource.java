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

import br.dev.mhc.financialassistantapi.dto.CurrencyTypeDTO;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.services.CurrencyTypeService;

@RestController
@RequestMapping(value = "/currencyType")
public class CurrencyTypeResource {

	@Autowired
	CurrencyTypeService service;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody CurrencyTypeDTO objDTO) {
		CurrencyType obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(obj.getUuid())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('BASIC_USER')")
	@GetMapping
	public ResponseEntity<List<CurrencyTypeDTO>> findAll() {
		List<CurrencyType> list = service.findAll();
		List<CurrencyTypeDTO> listDTO = list.stream().map(x -> new CurrencyTypeDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{uuid}")
	public ResponseEntity<Void> update(@Valid @RequestBody CurrencyTypeDTO objDTO, @PathVariable String uuid) {
		CurrencyType obj = service.fromDTO(objDTO);
		obj.setId(service.findByUuid(uuid).getId());
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}

package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.CurrencyTypeDTO;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.repositories.CurrencyTypeRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;
import br.dev.mhc.financialassistantapi.services.hgservice.HGService;
import br.dev.mhc.financialassistantapi.services.interfaces.CrudInterface;

@Service
public class CurrencyTypeService implements CrudInterface<CurrencyType, Long> {

	@Value("${api_hgfinance_limit-requests-per-day}")
	private Long limitRequestHgFinance;

	@Autowired
	CurrencyTypeRepository repository;

	@Autowired
	HGService hgService;

	@Transactional
	@Override
	public CurrencyType insert(CurrencyType obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		obj.setId(null);
		if (obj.getUuid() == null)
			obj.setUuid(UUID.randomUUID().toString());
		obj.setCreationMoment(Instant.now());
		obj.setLastUpdate(Instant.now());
		return repository.save(obj);
	}

	@Transactional
	@Override
	public CurrencyType update(CurrencyType obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		CurrencyType newObj = findById(obj.getId());
		newObj.setCode(obj.getCode());
		newObj.setDecimalDigits(obj.getDecimalDigits());
		newObj.setInitials(obj.getInitials());
		newObj.setName(obj.getName());
		newObj.setLastUpdate(Instant.now());
		return repository.save(newObj);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		CurrencyType obj = findById(id);
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		repository.delete(obj);
	}

	@Override
	public CurrencyType findById(Long id) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		Optional<CurrencyType> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + CurrencyType.class.getName()));
	}

	@Override
	public CurrencyType findByUuid(String uuid) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		Optional<CurrencyType> obj = repository.findByUuid(uuid);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Uuid: " + uuid + ", Type: " + CurrencyType.class.getName()));
	}

	@Override
	public List<CurrencyType> findAll() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		return repository.findAll();
	}

	@Override
	public Page<CurrencyType> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		updateCurrencyExchange();
		return repository.findAll(pageRequest);
	}

	@Transactional
	public void updateCurrencyExchange() {
		Long timeoutBetweenRequests = 86400000l / limitRequestHgFinance;
		Instant nextUpdate = repository.findMaxInstantLastUpdate().plusMillis(timeoutBetweenRequests);
		List<CurrencyType> list = repository.findAll();
		if (nextUpdate.compareTo(Instant.now()) < 0) {
			Map<String, BigDecimal> resultApi = hgService.findCurrencyExchange().getCurrencies();
			for (CurrencyType currency : list) {
				currency.setPriceInBRL(resultApi.get(currency.getCode()));
				currency.setLastUpdate(Instant.now());
				repository.save(currency);
			}
		}
	}

	public CurrencyType findByCode(String code) {
		updateCurrencyExchange();
		return repository.findByCode(code);
	}

	public CurrencyType fromDTO(CurrencyTypeDTO objDTO) {
		return new CurrencyType(null, objDTO.getUuid(), objDTO.getCode(), objDTO.getName(), objDTO.getInitials(),
				objDTO.getDecimalDigits(), objDTO.getPriceInBRL());
	}
}

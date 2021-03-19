package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.CurrencyTypeDTO;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.repositories.CurrencyTypeRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;
import br.dev.mhc.financialassistantapi.services.hgservice.HGService;

@Service
public class CurrencyTypeService {

	@Value("${api_hgfinance_limit-requests-per-day}")
	private Long limitRequestHgFinance;

	@Autowired
	CurrencyTypeRepository repository;

	@Autowired
	HGService hgService;

	@Transactional
	public CurrencyType insert(CurrencyType obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		return repository.save(obj);
	}

	@Transactional
	public CurrencyType update(CurrencyType obj) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		CurrencyType newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
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
			}
		}
	}

	public CurrencyType findById(Integer id) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		Optional<CurrencyType> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", Type: " + CurrencyType.class.getName()));
	}

	public CurrencyType findByCode(String code) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		return repository.findByCode(code);
	}

	public List<CurrencyType> findAll() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_OR_ADMIN);
		updateCurrencyExchange();
		return repository.findAll();
	}

	public CurrencyType fromDTO(CurrencyTypeDTO objDTO) {
		return new CurrencyType(objDTO.getId(), objDTO.getCode(), objDTO.getName(), objDTO.getInitials(),
				objDTO.getDecimalDigits(), objDTO.getPriceInBRL(), objDTO.getLastUpdate());
	}

	private void updateData(CurrencyType newObj, CurrencyType obj) {
		newObj.setCode(obj.getCode());
		newObj.setDecimalDigits(obj.getDecimalDigits());
		newObj.setInitials(obj.getInitials());
		newObj.setName(obj.getName());
	}
}

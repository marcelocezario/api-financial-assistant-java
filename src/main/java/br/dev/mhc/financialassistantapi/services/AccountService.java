package br.dev.mhc.financialassistantapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.mhc.financialassistantapi.dto.AccountDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.accounts.Wallet;
import br.dev.mhc.financialassistantapi.repositories.AccountRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.services.exceptions.AuthorizationException;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private UserService userService;

	@Transactional
	public Account insert(AccountDTO objDto) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null) {
			throw new AuthorizationException("Access denied");
		}

		Account obj = null;

		switch (objDto.getAccountType()) {
		case WALLET:
			obj = walletFromDTO(objDto);
			break;

		case BANK_ACCOUNT:
			obj = bankAccountFromDTO(objDto);
			break;

		case CREDIT_CARD:
			obj = creditCardFromDTO(objDto);
			break;
		}

		obj.setId(null);
		obj.setUser(userService.findById(userSS.getId()));
		obj = repository.save(obj);

		return obj;
	}

	@Transactional
	public Account update(AccountDTO objDTO) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null || !objDTO.getId().equals(userSS.getId())) {
			throw new AuthorizationException("Access denied");
		}
		
		switch (objDTO.getAccountType()) {
		case WALLET:
			
			
			break;

		case BANK_ACCOUNT:
			
			break;

		case CREDIT_CARD:
			
			break;

		default:
			break;
		}


		return null;
	}

	private void updateData(Wallet newObj, Wallet obj) {
		newObj.setName(obj.getName());
	}

	private void updateData(BankAccount newObj, BankAccount obj) {
		newObj.setName(obj.getName());
		newObj.setBankInterestRate(obj.getBankInterestRate());
		newObj.setLimitValue(obj.getLimitValueBankAccount());
	}

	private void updateData(CreditCard newObj, CreditCard obj) {
		newObj.setName(obj.getName());
		newObj.setClosingDay(obj.getClosingDay());
		newObj.setDueDay(obj.getDueDay());
		newObj.setLimitValueCard(obj.getLimitValueCard());
	}

	public Account findById(Long id) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null) {
			throw new AuthorizationException("Access denied");
		}
		Optional<Account> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}
	
	public Wallet walletFromDTO(AccountDTO objDTO) {
		return new Wallet(objDTO.getId(), objDTO.getName(), objDTO.getBalance());
	}

	public BankAccount bankAccountFromDTO(AccountDTO objDTO) {
		return new BankAccount(objDTO.getId(), objDTO.getName(), objDTO.getBalance(), objDTO.getBankInterestRate(),
				objDTO.getLimitValueBankAccount());
	}

	public CreditCard creditCardFromDTO(AccountDTO objDTO) {

		return new CreditCard(objDTO.getId(), objDTO.getName(), objDTO.getBalance(), objDTO.getClosingDay(),
				objDTO.getDueDay(), objDTO.getLimitValueCard());
	}
}

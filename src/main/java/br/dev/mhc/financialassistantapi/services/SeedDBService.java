package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.CategoryRepository;
import br.dev.mhc.financialassistantapi.repositories.CurrencyTypeRepository;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.utils.Tool;

@Service
public class SeedDBService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CurrencyTypeRepository currencyRepository;

	@Autowired
	private CurrencyTypeService currencyService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	public void seedCategory() {
		User user = null;
		List<Category> categories = new ArrayList<>();
		categories.add(new Category(1L, "Alimentação", "", user, true));
		categories.add(new Category(2L, "Automotivo", "", user, true));
		categories.add(new Category(3L, "Cartão de crédito", "", user, true));
		categories.add(new Category(4L, "Doações/Presentes", "", user, true));
		categories.add(new Category(5L, "Educação", "", user, true));
		categories.add(new Category(6L, "Impostos/Tributos", "", user, true));
		categories.add(new Category(7L, "Investimento", "", user, true));
		categories.add(new Category(8L, "Lazer", "", user, true));
		categories.add(new Category(9L, "Moradia", "", user, true));
		categories.add(new Category(10L, "Pet", "", user, true));
		categories.add(new Category(11L, "Produtos de Limpeza", "", user, true));
		categories.add(new Category(12L, "Saque", "", user, true));
		categories.add(new Category(13L, "Saúde", "", user, true));
		categories.add(new Category(14L, "Seguros", "", user, true));
		categories.add(new Category(15L, "Tarifas Bancárias", "", user, true));
		categories.add(new Category(16L, "Trabalho", "", user, true));
		categories.add(new Category(17L, "Transporte", "", user, true));
		categories.add(new Category(18L, "Vestuário", "", user, true));
		categories.add(new Category(19L, "Viagens", "", user, true));

		Long registers = categoryRepository.count();
		for (Long i = registers; i > 0; i--) {
			categories.remove(0);
		}
		categoryRepository.saveAll(categories);
	}

	public void seedCurrencyType() {
		Instant momentUpdate = Instant.now().minusMillis(300000L);
		List<CurrencyType> currencies = new ArrayList<>();
		currencies.add(new CurrencyType(1, "BRL", "Real", "R$", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(2, "USD", "Dólar Americano", "US$", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(3, "EUR", "Euro", "€", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(4, "GBP", "Libra Esterlina", "£", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(5, "ARS", "Peso Argentino", "ARS", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(6, "CAD", "Dólar Canadense", "C$", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(8, "AUD", "Dólar Australiano", "AU$", 2, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(9, "JPY", "Iene", "¥", 0, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(10, "CNY", "Renminbi", "CN¥", 1, BigDecimal.ZERO, momentUpdate));
		currencies.add(new CurrencyType(11, "BTC", "Bitcoin", "₿", 5, BigDecimal.ZERO, momentUpdate));

		Long registers = currencyRepository.count();
		for (Long i = registers; i > 0; i--) {
			currencies.remove(0);
		}
		currencyRepository.saveAll(currencies);
		currencyService.updateCurrencyExchange();
	}

	public void seedUser() {
		Long registers = userRepository.count();
		if (registers == 0) {
			String password = Tool.stringGenerator(10);
			System.out.println("Password generated: " + password);
			User user = new User(null, "Administrador", "Admin", "admin@mhc.dev.br", passwordEncoder.encode(password),
					Instant.now(), null, null);
			userRepository.save(user);
		}
	}
}

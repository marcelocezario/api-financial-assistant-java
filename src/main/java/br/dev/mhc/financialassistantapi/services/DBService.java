package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.CategoryRepository;
import br.dev.mhc.financialassistantapi.repositories.CurrencyTypeRepository;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;

@Service
public class DBService {

	@Value("${dbseed.admin_user.email}")
	private String adminEmail;

	@Value("${dbseed.admin_user.password}")
	private String adminPassword;

	@Autowired
	private CurrencyTypeRepository currencyRepository;

	@Autowired
	private CurrencyTypeService currencyService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private CategoryRepository categoryRepository;

	public void databaseSeeding() throws ParseException {
		// add currencies
		Instant momentUpdate = Instant.now().minusMillis(300000L);
		List<CurrencyType> currencies = new ArrayList<>();
		currencies.add(new CurrencyType(1L, UUID.nameUUIDFromBytes("BRL".getBytes()).toString(), "BRL", "Real", "R$", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(2L, UUID.nameUUIDFromBytes("USD".getBytes()).toString(), "USD", "Dólar Americano", "US$", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(3L, UUID.nameUUIDFromBytes("EUR".getBytes()).toString(), "EUR", "Euro", "€", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(4L, UUID.nameUUIDFromBytes("GPD".getBytes()).toString(), "GBP", "Libra Esterlina", "£", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(5L, UUID.nameUUIDFromBytes("ARS".getBytes()).toString(), "ARS", "Peso Argentino", "ARS", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(6L, UUID.nameUUIDFromBytes("CAD".getBytes()).toString(), "CAD", "Dólar Canadense", "C$", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(8L, UUID.nameUUIDFromBytes("AUD".getBytes()).toString(), "AUD", "Dólar Australiano", "AU$", 2, BigDecimal.ZERO));
		currencies.add(new CurrencyType(9L, UUID.nameUUIDFromBytes("JPY".getBytes()).toString(), "JPY", "Iene", "¥", 0, BigDecimal.ZERO));
		currencies.add(new CurrencyType(10L, UUID.nameUUIDFromBytes("CNY".getBytes()).toString(), "CNY", "Renminbi", "CN¥", 1, BigDecimal.ZERO));
		currencies.add(new CurrencyType(11L, UUID.nameUUIDFromBytes("BTC".getBytes()).toString(), "BTC", "Bitcoin", "₿", 5, BigDecimal.ZERO));
		Long registers = currencyRepository.count();
		for (CurrencyType x : currencies) {
			if (x.getId() < registers) {
				currencies.remove(x);
			} else {
				x.setCreationMoment(momentUpdate);
				x.setLastUpdate(momentUpdate);
			}
		}
		currencyRepository.saveAll(currencies);
		currencyService.updateCurrencyExchange();

		// add user admin
		User user = userRepository.findById(1L).orElse(null);
		if (user == null) {
			user = new User(1l, UUID.nameUUIDFromBytes(("adminUser").getBytes()).toString(), "Administrador", "Admin", adminEmail,
					pe.encode(adminPassword), null, null);
			user.addProfile(Profile.ADMIN);
			user = userService.insert(user);
		}

		// add basic user
		User basicUser = userRepository.findById(2L).orElse(null);
		if (basicUser == null) {
			basicUser = new User(2L, UUID.nameUUIDFromBytes(("basicUser").getBytes()).toString(), "Marcelo", "marcelo",
					"marcelocezario@gmail.com", pe.encode("123456"), null, null);
			basicUser = userService.insert(basicUser);
		}

		// add categories
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
		for (Long i = categoryRepository.count(); i > 0; i--) {
			categories.remove(0);
		}
		categoryRepository.saveAll(categories);

	}
}

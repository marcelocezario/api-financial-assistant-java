package br.dev.mhc.financialassistantapi.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.Category;
import br.dev.mhc.financialassistantapi.entities.Entry;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.accounts.Wallet;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.AccountRepository;
import br.dev.mhc.financialassistantapi.repositories.CategoryRepository;
import br.dev.mhc.financialassistantapi.repositories.EntryRepository;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntryRepository entryRepository;

	public void instantiateTestDatabase() throws ParseException {

		User u1 = new User(null, "Henrique Cezário", "Henrique", "marcelocezario@gmail.com", pe.encode("111111"),
				Instant.parse("2021-02-01T10:00:00Z"), null);
		u1.addProfile(Profile.ADMIN);

		User u2 = new User(null, "Jennifer", "Jenn", "jenni@gmail.com", pe.encode("222222"),
				Instant.parse("2021-02-02T10:00:00Z"), null);
		User u3 = new User(null, "Niele Angela", "Dekhan", "dekhan@gmail.com", pe.encode("333333"),
				Instant.parse("2021-02-03T10:00:00Z"), null);
		User u4 = new User(null, "Bruno Rafael", "blk", "blk@gmail.com", pe.encode("444444"),
				Instant.parse("2021-02-04T10:00:00Z"), null);
		User u5 = new User(null, "Bruno Cezario", "Bruno", "bruno@gmail.com", pe.encode("555555"),
				Instant.parse("2021-02-05T10:00:00Z"), null);
		User u6 = new User(null, "Carlos José Cezario", "Carlos", "carlos@gmail.com", pe.encode("666666"),
				Instant.parse("2021-02-06T10:00:00Z"), null);

		Account a1 = new Wallet(null, "Carteira", new BigDecimal("0"), u1);
		Account a2 = new BankAccount(null, "Conta corrente", new BigDecimal("0"), new BigDecimal("8.0"),
				new BigDecimal("1000.0"), u1);
		Account a3 = new CreditCard(null, "Cartão de crédito", new BigDecimal("0"), 15, 25, new BigDecimal("200.0"),
				u1);
		Account a4 = new Wallet(null, "Carteira", new BigDecimal("0"), u2);
		Account a5 = new BankAccount(null, "Conta corrente", new BigDecimal("0"), new BigDecimal("10.0"),
				new BigDecimal("500.0"), u2);
		Account a6 = new CreditCard(null, "Cartão de crédito", new BigDecimal("0"), 10, 20, new BigDecimal("500.0"),
				u2);

		Category c1 = new Category(null, "Alimentação", "icon-alim.svg", u1);
		Category c2 = new Category(null, "Combustível", "icon-vehicle.svg", u1);
		Category c3 = new Category(null, "Trabalho", "icon-work.svg", u1);

		Category c4 = new Category(null, "Alimentação", "icon-alim.svg", u2);
		Category c5 = new Category(null, "Combustível", "icon-vehicle.svg", u2);
		Category c6 = new Category(null, "Trabalho", "icon-work.svg", u2);

		Entry e1 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);
		Entry e2 = new Entry(null, Instant.now(), new BigDecimal("15.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);
		Entry e3 = new Entry(null, Instant.now(), new BigDecimal("20.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);
		Entry e4 = new Entry(null, Instant.now(), new BigDecimal("25.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);
		Entry e5 = new Entry(null, Instant.now(), new BigDecimal("30.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);
		Entry e6 = new Entry(null, Instant.now(), new BigDecimal("50.0"), "Dinheiro recebido", Instant.now(),
				Instant.now(), 1, 1, EntryType.CREDIT, a4, u2, null);
		Entry e7 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2, null);

		Entry e8 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2, null);
		Entry e9 = new Entry(null, Instant.now(), new BigDecimal("15.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2, null);
		Entry e10 = new Entry(null, Instant.now(), new BigDecimal("16.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2, null);
		Entry e11 = new Entry(null, Instant.now(), new BigDecimal("17.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2, null);
		Entry e12 = new Entry(null, Instant.now(), new BigDecimal("20.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2, null);

		a4.addEntry(e1);
		a4.addEntry(e2);
		a4.addEntry(e3);
		a4.addEntry(e4);
		a4.addEntry(e5);
		a4.addEntry(e6);
		a4.addEntry(e7);

		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));
		accountRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6));
		categoryRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
		entryRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12));
	}
}

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
import br.dev.mhc.financialassistantapi.entities.EntryCategory;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.accounts.BankAccount;
import br.dev.mhc.financialassistantapi.entities.accounts.CreditCard;
import br.dev.mhc.financialassistantapi.entities.accounts.Wallet;
import br.dev.mhc.financialassistantapi.entities.enums.EntryType;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.AccountRepository;
import br.dev.mhc.financialassistantapi.repositories.CategoryRepository;
import br.dev.mhc.financialassistantapi.repositories.EntryCategoryRepository;
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

	@Autowired
	private EntryCategoryRepository entryCategoryRepository;

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
		
		Category c1 = new Category(null, "Alimentação", "", u2, true);
		Category c2 = new Category(null, "Automotivo", "", u2, true);
		Category c3 = new Category(null, "Cartão de crédito", "", u2, true);
		Category c4 = new Category(null, "Doações/Presentes", "", u2, true);
		Category c5 = new Category(null, "Educação", "", u1, true);
		Category c6 = new Category(null, "Impostos/Tributos", "", u1, true);
		Category c7 = new Category(null, "Investimento", "", u1, true);
		Category c8 = new Category(null, "Lazer", "", u1, true);
		Category c9 = new Category(null, "Moradia", "", u1, true);
		Category c10 = new Category(null, "Pet", "", u1, true);
		Category c11 = new Category(null, "Produtos de Limpeza", "", u1, true);
		Category c12 = new Category(null, "Saque", "", u1, true);
		Category c13 = new Category(null, "Saúde", "", u1, true);
		Category c14 = new Category(null, "Seguros", "", u1, true);
		Category c15 = new Category(null, "Tarifas Bancárias", "", u1, true);
		Category c16 = new Category(null, "Trabalho", "", u1, true);
		Category c17 = new Category(null, "Transporte", "", u1, true);
		Category c18 = new Category(null, "Vestuário", "", u1, true);
		Category c19 = new Category(null, "Viagens", "", u1, true);


		Entry e1 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);
		Entry e2 = new Entry(null, Instant.now(), new BigDecimal("15.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);
		Entry e3 = new Entry(null, Instant.now(), new BigDecimal("20.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);
		Entry e4 = new Entry(null, Instant.now(), new BigDecimal("25.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);
		Entry e5 = new Entry(null, Instant.now(), new BigDecimal("30.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);
		Entry e6 = new Entry(null, Instant.now(), new BigDecimal("50.0"), "Dinheiro recebido", Instant.now(),
				Instant.now(), 1, 1, EntryType.CREDIT, a4, u2);
		Entry e7 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), Instant.now(),
				1, 1, EntryType.DEBIT, a4, u2);

		Entry e8 = new Entry(null, Instant.now(), new BigDecimal("10.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2);
		Entry e9 = new Entry(null, Instant.now(), new BigDecimal("15.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2);
		Entry e10 = new Entry(null, Instant.now(), new BigDecimal("16.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2);
		Entry e11 = new Entry(null, Instant.now(), new BigDecimal("17.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2);
		Entry e12 = new Entry(null, Instant.now(), new BigDecimal("20.0"), "Compra lanche", Instant.now(), null, 1, 1,
				EntryType.DEBIT, null, u2);

		a4.addEntry(e1);
		a4.addEntry(e2);
		a4.addEntry(e3);
		a4.addEntry(e4);
		a4.addEntry(e5);
		a4.addEntry(e6);
		a4.addEntry(e7);
		
		EntryCategory ec1 = new EntryCategory(e1, c1, new BigDecimal("10.00"));
		EntryCategory ec2 = new EntryCategory(e2, c1, new BigDecimal("15.00"));
		EntryCategory ec3 = new EntryCategory(e3, c1, new BigDecimal("20.00"));
		EntryCategory ec4 = new EntryCategory(e4, c1, new BigDecimal("15.00"));
		EntryCategory ec5 = new EntryCategory(e5, c1, new BigDecimal("30.00"));
		EntryCategory ec6 = new EntryCategory(e6, c3, new BigDecimal("50.00"));
		EntryCategory ec7 = new EntryCategory(e7, c1, new BigDecimal("10.00"));
		EntryCategory ec8 = new EntryCategory(e8, c1, new BigDecimal("10.00"));
		EntryCategory ec9 = new EntryCategory(e9, c1, new BigDecimal("15.00"));
		
		EntryCategory ec10 = new EntryCategory(e10, c1, new BigDecimal("10.00"));
		EntryCategory ec11 = new EntryCategory(e10, c2, new BigDecimal("6.00"));
		
		EntryCategory ec12 = new EntryCategory(e11, c1, new BigDecimal("17.00"));
		EntryCategory ec13 = new EntryCategory(e12, c1, new BigDecimal("20.00"));


		userRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));
		accountRepository.saveAll(Arrays.asList(a1, a2, a3, a4, a5, a6));
		categoryRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19));
		entryRepository.saveAll(Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12));
		entryCategoryRepository.saveAll(Arrays.asList(ec1, ec2, ec3, ec4, ec5, ec6, ec7, ec8, ec9, ec10, ec11, ec12, ec13));
	}
}

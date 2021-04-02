package br.dev.mhc.financialassistantapi.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.dev.mhc.financialassistantapi.dto.UserDTO;
import br.dev.mhc.financialassistantapi.dto.UserNewDTO;
import br.dev.mhc.financialassistantapi.entities.Account;
import br.dev.mhc.financialassistantapi.entities.CurrencyType;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.exceptions.DataIntegrityException;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;
import br.dev.mhc.financialassistantapi.services.interfaces.CrudInterface;

@Service
public class UserService implements CrudInterface<User, Long> {

	@Autowired
	private DefaultService defaultService;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository repository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private imageService imageService;

	@Autowired
	private CurrencyTypeService currencyService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	@Transactional
	@Override
	public User insert(User obj) {
		obj.setCreationMoment(Instant.now());
		if (obj.getUuid() == null) {
			obj.setUuid(UUID.randomUUID().toString());
		}
		if (obj.getDefaultCurrencyType() == null) {
			obj.setDefaultCurrencyType(defaultService.defaultCurrency());
		}
		for (Account account : defaultService.defaultUserAccounts()) {
			account.setId(null);
			if (account.getUuid() == null)
				account.setUuid(UUID.randomUUID().toString());
			account.setCreationMoment(Instant.now());
			account.setCurrencyType(obj.getDefaultCurrencyType());
			account.setUser(obj);
			obj.addAccount(account);
		}
		obj = repository.save(obj);
//		emailService.sendUserConfirmationEmail(obj);
		emailService.sendUserConfirmationHtmlEmail(obj);
		return obj;
	}

	@Transactional
	@Override
	public User update(User obj) {
		AuthService.validatesUserAuthorization(obj.getId(), AuthorizationType.USER_ONLY);
		User newObj = findById(obj.getId());
		newObj.setName(obj.getName());
		newObj.setNickname(obj.getNickname());
		newObj.setEmail(obj.getEmail());
		newObj.setImageUrl(obj.getImageUrl());
		newObj.setLastUpdate(Instant.now());
		newObj.setDefaultCurrencyType(obj.getDefaultCurrencyType());
		return repository.save(newObj);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		AuthService.validatesUserAuthorization(id, AuthorizationType.USER_OR_ADMIN);
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You cannot delete a user with linked accounts");
		}
	}

	@Override
	public User findById(Long id) {
		AuthService.validatesUserAuthorization(id, AuthorizationType.USER_OR_ADMIN);

		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}

	@Override
	public User findByUuid(String uuid) {
		AuthService.validatesUserAuthorization(uuid, AuthorizationType.USER_OR_ADMIN);

		Optional<User> obj = repository.findByUuid(uuid);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Uuid: " + uuid + ", Type: " + User.class.getName()));
	}

	@Override
	public List<User> findAll() {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.ADMIN_ONLY);
		return repository.findAll();
	}

	@Override
	public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User fromDTO(UserDTO objDto) {
		return new User(null, objDto.getUuid(), objDto.getName(), objDto.getNickname(), objDto.getEmail(), null,
				objDto.getImageUrl(), currencyService.fromDTO(objDto.getDefaultCurrencyType()));
	}

	public User fromDTO(UserNewDTO objDTO) {
		if (objDTO.getNickname() == null || objDTO.getNickname().equals("")) {
			objDTO.setNickname(objDTO.getName().split(" ")[0]);
		}
		CurrencyType currency;
		if (objDTO.getDefaultCurrencyType() == null) {
			currency = defaultService.defaultCurrency();
		} else {
			currency = currencyService.fromDTO(objDTO.getDefaultCurrencyType());
		}
		return new User(null, null, objDTO.getName(), objDTO.getNickname(), objDTO.getEmail(),
				pe.encode(objDTO.getPassword()), null, currency);
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + userSS.getUuid() + ".jpg";

		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

		User user = findByUuid(userSS.getUuid());
		user.setImageUrl(uri.toString());
		update(user);

		return uri;
	}
}

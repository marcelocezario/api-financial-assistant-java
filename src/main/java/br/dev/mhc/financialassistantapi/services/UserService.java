package br.dev.mhc.financialassistantapi.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

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
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.security.enums.AuthorizationType;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.exceptions.DataIntegrityException;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

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

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	@Transactional
	public User insert(User obj) {
		obj.setRegistrationMoment(Instant.now());
		obj = repository.save(obj);
//		emailService.sendUserConfirmationEmail(obj);
		emailService.sendUserConfirmationHtmlEmail(obj);
		return obj;
	}

	@Transactional
	public User update(User obj) {
		AuthService.validatesUserAuthorization(obj.getId(), AuthorizationType.USER_ONLY);
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setNickname(obj.getNickname());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Long id) {
		AuthService.validatesUserAuthorization(id, AuthorizationType.USER_OR_ADMIN);

		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("You cannot delete a user with linked accounts");
		}
	}

	public List<User> findAll() {
		return repository.findAll();
	}

	public Page<User> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public User findById(Long id) {
		AuthService.validatesUserAuthorization(id, AuthorizationType.USER_OR_ADMIN);

		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getNickname(), objDto.getEmail(), null,
				objDto.getRegistrationMoment(), objDto.getImageUrl());
	}

	public User fromDTO(UserNewDTO objDTO) {
		if (objDTO.getNickname() == null || objDTO.getNickname().equals("")) {
			objDTO.setNickname(objDTO.getName().split(" ")[0]);
		}
		User user = new User(null, objDTO.getName(), objDTO.getNickname(), objDTO.getEmail(),
				pe.encode(objDTO.getPassword()), null, null);
		return user;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSpringSecurity userSS = AuthService.getAuthenticatedUserSpringSecurity();
		AuthService.validatesUserAuthorization(userSS.getId(), AuthorizationType.USER_ONLY);

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + userSS.getId() + ".jpg";

		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

		User user = findById(userSS.getId());
		user.setImageUrl(uri.toString());
		repository.save(user);

		return uri;
	}
}

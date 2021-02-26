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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.dev.mhc.financialassistantapi.dto.UserDTO;
import br.dev.mhc.financialassistantapi.dto.UserNewDTO;
import br.dev.mhc.financialassistantapi.entities.User;
import br.dev.mhc.financialassistantapi.entities.enums.Profile;
import br.dev.mhc.financialassistantapi.repositories.UserRepository;
import br.dev.mhc.financialassistantapi.security.UserSpringSecurity;
import br.dev.mhc.financialassistantapi.services.email.EmailService;
import br.dev.mhc.financialassistantapi.services.exceptions.AuthorizationException;
import br.dev.mhc.financialassistantapi.services.exceptions.DataIntegrityException;
import br.dev.mhc.financialassistantapi.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {

	public static UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

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

	@Transactional
	public User insert(User obj) {
		obj.setActive(true);
		obj.setRegistrationMoment(Instant.now());
		obj.setLastAccess(obj.getRegistrationMoment());
		obj = repository.save(obj);
//		emailService.sendUserConfirmationEmail(obj);
		emailService.sendUserConfirmationHtmlEmail(obj);
		return obj;
	}

	@Transactional
	public User update(User obj) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null || !userSS.hasRole(Profile.ADMIN) && !obj.getId().equals(userSS.getId())) {
			throw new AuthorizationException("Access denied");
		}

		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void updateData(User newObj, User obj) {
		newObj.setNickname(obj.getNickname());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Long id) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null || !userSS.hasRole(Profile.ADMIN) && !id.equals(userSS.getId())) {
			throw new AuthorizationException("Access denied");
		}

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
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null || !userSS.hasRole(Profile.ADMIN) && !id.equals(userSS.getId())) {
			throw new AuthorizationException("Access denied");
		}

		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + User.class.getName()));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getNickname(), objDto.getEmail(), null, objDto.getRegistrationMoment(),
				objDto.getLastAccess(), objDto.getImageUrl(), objDto.isActive());
	}

	public User fromDTO(UserNewDTO objDTO) {
		User user = new User(null, objDTO.getNickname(), objDTO.getEmail(), pe.encode(objDTO.getPassword()), null, null,
				null, true);
		return user;
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) {
		UserSpringSecurity userSS = UserService.authenticated();
		if (userSS == null) {
			throw new AuthorizationException("Access denied");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		String fileName = prefix + userSS.getId() + ".jpg";
		
		URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

		User user = findById(userSS.getId());
		user.setImageUrl(uri.toString());
		repository.save(user);
		
		return uri;
	}
}

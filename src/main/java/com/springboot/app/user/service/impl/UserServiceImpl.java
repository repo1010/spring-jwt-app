package com.springboot.app.user.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.springboot.app.user.model.UserEntity;
import com.springboot.app.user.repository.UserRepository;
import com.springboot.app.user.service.UserService;

/**
 * UserServiceImpl provides several methods to perform CRUD operation on entity
 * of type UserEntity.
 * 
 * @author VISHAL
 *
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	Validator customValidator;

	/**
	 * Find all entities of type UserEntity
	 * 
	 * @return List<UserEntity>
	 */
	@Override
	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Find an entity of type UserEntity
	 * 
	 * @param Long id
	 * @return Optional<UserEntity>
	 */
	@Override
	public UserEntity find(long id) {
		return userRepository.findById(id).get();
	}

	/**
	 * Save an entity of type UserEntity
	 * 
	 * @param List<UserEntity> users
	 * @return List<UserEntity>
	 */
	@Override
	public List<UserEntity> save(List<UserEntity> users) {
		validate(users);
		return userRepository.saveAll(users);
	}

	/**
	 * Update an entity of Type UserEntity
	 * 
	 * @param UserEntity user
	 * @param Long       Id
	 * @return UserEntity
	 */
	@Override
	public UserEntity update(UserEntity user, long id) {
		validate(Arrays.asList(user));

		UserEntity retUser = find(id);
		if (retUser != null) {
			user.setId(id);
			return userRepository.save(user);
		}
		throw new RuntimeException("User Entity with id: " + id + " doesn't exit.");

	}

	/**
	 * Delete an entity of Type UserEntity
	 * 
	 * @param Long id
	 * @return void
	 */
	@Override
	public void delete(long id) {
		userRepository.deleteById(id);
	}

	/**
	 * Perform validation
	 * 
	 * @param tyres
	 */
	private void validate(List<UserEntity> users) {
		List<String> constraintValdnList = new ArrayList<>();

		users.stream().forEach(user -> {
			Set<ConstraintViolation<UserEntity>> violations = customValidator.validate(user);

			if (violations.size() > 0)
				violations.stream().forEach(violation -> {
					String joinedMsg = StringUtils.concat(violation.getMessageTemplate(), " for property: ",
							violation.getPropertyPath(), " having invalid value: ",
							violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "null");
					logger.error(joinedMsg);
					constraintValdnList.add(joinedMsg);
				});
		});
		if (!constraintValdnList.isEmpty())
			throw new javax.validation.ConstraintViolationException(constraintValdnList.toString(), null);
	}

	/**
	 * Perform login Validation
	 * 
	 * @param user
	 */
	@Override
	public void loginValidation(UserEntity user) {
		this.validate(Arrays.asList(user));

	}
}

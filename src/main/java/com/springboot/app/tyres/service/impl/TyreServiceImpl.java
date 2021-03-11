package com.springboot.app.tyres.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.springboot.app.tyre.model.Tyre;
import com.springboot.app.tyre.repository.TyreRepository;
import com.springboot.app.tyre.service.TyreService;
import com.springboot.app.user.model.UserEntity;
import com.springboot.app.util.JwtRequestFilter;

/**
 * TyresServiceImpl provides several methods to perform CRUD operations on Tyre
 * entity.
 * 
 * @author VISHAL
 *
 */
@Service
public class TyreServiceImpl implements TyreService {

	private static final Logger logger = LoggerFactory.getLogger(TyreServiceImpl.class);

	private TyreRepository tyresRepository;

	@Autowired
	Validator customValidator;

	private boolean isUser = false;
	private String username;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	TyreServiceImpl(TyreRepository tyresRepository) {
		this.tyresRepository = tyresRepository;
	}

	/**
	 * Get All Tyres
	 * 
	 * @return List<Tyre>
	 */
	@Override
	public List<Tyre> getAllTyres() {
		this.isUser = this.jwtRequestFilter.isUser().isPresent();
		this.username = this.jwtRequestFilter.username();
		return this.isUser ? tyresRepository.findAllByUsername(this.username) : tyresRepository.findAll();

	}

	/**
	 * Save the Tyres
	 * 
	 * @param List<Tyre> tyres
	 * @return List<Tyre>
	 * 
	 */
	@Override
	public List<Tyre> save(List<Tyre> tyres) {
		this.isUser = this.jwtRequestFilter.isUser().isPresent();
		this.username = this.jwtRequestFilter.username();

		validate(tyres);

		if (isUser)
			tyres.forEach(tyre -> tyre.setUsername(this.username));

		// Load user for storing into Tyre type input object
		tyres.forEach(tyre -> {
			UserEntity user = (UserEntity) tyresRepository.loadUser(tyre.getUsername());
			if (user != null)
				tyre.setUser(user);
			else
				throw new ConstraintViolationException("UserEntity is not persisted", null,
						"foreign key constraint violation");
		});

		return tyresRepository.saveAll(tyres);
	}

	/**
	 * find a Tyre by Id
	 * 
	 * @param Long id
	 * @return Tyre
	 * 
	 */
	@Override
	public Tyre find(Long id) {
		this.isUser = this.jwtRequestFilter.isUser().isPresent();
		this.username = this.jwtRequestFilter.username();
		return isUser ? tyresRepository.findByIdAndUsername(id, this.username) : tyresRepository.findById(id).get();
	}

	/**
	 * Update a Tyre
	 * 
	 * @param Tyre tyre
	 * @param Long id
	 * @return Tyre
	 */
	@Override
	public Tyre update(Tyre tyre, Long id) throws RuntimeException {
		this.isUser = this.jwtRequestFilter.isUser().isPresent();
		this.username = this.jwtRequestFilter.username();

		validate(Arrays.asList(tyre));

		Tyre retTyre = find(id);
		if (retTyre != null && retTyre.getId() != null) {

			// set id
			tyre.setId(id);

			// set explicitly USERNAME for USER role type
			if (isUser)
				tyre.setUsername(this.username);

			// set user
			UserEntity user = (UserEntity) tyresRepository.loadUser(tyre.getUsername());
			if (user != null)
				tyre.setUser(user);
			else
				throw new ConstraintViolationException("UserEntity is not persisted", null,
						"foreign key constraint violation");

			return tyresRepository.save(tyre);
		}
		throw new RuntimeException("Entity with id: " + id + " doesn't exits for given username");

	}

	/**
	 * Delete Tyre by id
	 * 
	 * @param Long id
	 * @return boolean
	 */
	@Override
	public boolean delete(Long id) {
		this.isUser = this.jwtRequestFilter.isUser().isPresent();
		this.username = this.jwtRequestFilter.username();

		try {
			if (isUser)
				tyresRepository.deleteByIdAndUsername(id, this.username);
			else
				tyresRepository.deleteById(id);

			return true;
		} catch (Exception e) {
			throw new RuntimeException("Deletion process couldn't completed", e);

		}

	}

	/**
	 * Perform validation
	 * 
	 * @param tyres
	 */
	private void validate(List<Tyre> tyres) {
		List<String> constraintValdnList = new ArrayList<>();
		tyres.stream().forEach(tyre -> {
			Set<ConstraintViolation<Tyre>> violations = customValidator.validate(tyre);

			if (violations.size() > 0)
				violations.stream().forEach(violation -> {
					String joinedMsg = StringUtils.concat(violation.getMessage(), " for property: ",
							violation.getPropertyPath(), " having invalid value: ",
							violation.getInvalidValue() != null ? violation.getInvalidValue().toString() : "null");
					logger.error(joinedMsg);
					constraintValdnList.add(joinedMsg);
				});

		});
		if (!constraintValdnList.isEmpty())
			throw new javax.validation.ConstraintViolationException(constraintValdnList.toString(), null);
	}

}

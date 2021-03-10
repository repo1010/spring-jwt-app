package com.springboot.app.tyres.service.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private TyreRepository tyresRepository;

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
		if (isUser)
			tyres.forEach(tyre -> tyre.setUsername(this.username));

		// Load user for storing into Tyre type input object
		tyres.forEach(tyre -> {
			Optional<UserEntity> userEntity = Optional
					.ofNullable((UserEntity) tyresRepository.loadUser(tyre.getUsername()));
			if (userEntity.isPresent())
				tyre.setUser(userEntity.get());
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

		Tyre retTyre = find(id);
		if (retTyre != null && retTyre.getId() != null) {

			// set id
			tyre.setId(id);

			// set explicitly USERNAME for USER role type
			if (isUser)
				tyre.setUsername(this.username);

			// set user
			Optional<UserEntity> userEntity = Optional
					.ofNullable((UserEntity) tyresRepository.loadUser(tyre.getUsername()));
			if (userEntity.isPresent())
				tyre.setUser(userEntity.get());
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

}

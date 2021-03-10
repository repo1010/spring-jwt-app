package com.springboot.app.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	private UserRepository userRepository;

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
	public Optional<UserEntity> find(long id) {

		return userRepository.findById(id);
	}

	/**
	 * Save an entity of type UserEntity
	 * 
	 * @param List<UserEntity> users
	 * @return List<UserEntity>
	 */
	@Override
	public List<UserEntity> save(List<UserEntity> users) {
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
		Optional<UserEntity> retUser = find(id);
		if (retUser.isPresent()) {
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
}

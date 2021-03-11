package com.springboot.app.user.service;

import java.util.List;

import com.springboot.app.user.model.UserEntity;

/**
 * UserService provides several CRUD specific methods
 * @author VISHAL
 *
 */
public interface UserService {

	/**
	 * Find all entities of type UserEntity .
	 * @return List<UserEntity>
	 */
	List<UserEntity> findAll();

	/**
	 * Find a UserEntity by id
	 * @param id
	 * @return Optional<UserEntity>
	 */
	UserEntity find(long id);

	/**
	 * Save an entity of type UserEntity
	 * @param user
	 * @return List<UserEntity> 
	 */
	List<UserEntity> save(List<UserEntity> users);

	/**
	 * Update an USerEntity by by Id
	 * @param user
	 * @param id
	 * @return UserEntity
	 */
	UserEntity update(UserEntity user, long id);

	/**
	 * Delete an UserEntity by id
	 * @param id
	 * @return void
	 */
	void delete(long id);

	/**
	 * Login validation
	 * @param user
	 */
	void loginValidation(UserEntity user);
}

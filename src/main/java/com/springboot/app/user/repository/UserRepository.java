package com.springboot.app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.app.user.model.UserEntity;

/**
 * UserRepository provides methods to perform CRUD operations on UserEntity
 * entities.
 * 
 * @author VISHAL
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * Find an UserEntity by username
	 * 
	 * @param username
	 * @return UserEntity
	 */
	public UserEntity findByUsername(String username);

}

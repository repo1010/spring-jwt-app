package com.springboot.app.tyre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.tyre.model.Tyre;

/**
 * TyreRepository provides several methods to perform CRUD operations on Tyre
 * entity.
 * 
 * @author VISHAL
 *
 */
@Repository
public interface TyreRepository extends JpaRepository<Tyre, Long> {

	/**
	 * Find all Tyre entities by username
	 * 
	 * @param username
	 * @return List<Tyre>
	 */
	List<Tyre> findAllByUsername(@Param("username") String username);

	/**
	 * Find a Tyre entity by id and username
	 * 
	 * @param id
	 * @param username
	 * @return
	 */
	@Query("from Tyre where id= :id and username = :username")
	Tyre findByIdAndUsername(@Param("id") Long id, @Param("username") String username);

	/**
	 * Delete a Tyre entity by id and username
	 * 
	 * @param id
	 * @param username
	 * @return int
	 */
	@Transactional
	@Modifying
	@Query("delete from Tyre where id= :id and username = :username")
	int deleteByIdAndUsername(@Param("id") Long id, @Param("username") String username);

	/**
	 * Load a UserEntity.
	 * 
	 * @param username
	 * @return
	 */
	@Query("from UserEntity where username = :username")
	Object loadUser(@Param("username") String username);

}

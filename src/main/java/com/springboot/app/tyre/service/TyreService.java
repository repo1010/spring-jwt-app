package com.springboot.app.tyre.service;

import java.util.List;

import com.springboot.app.tyre.model.Tyre;

/**
 * TyreService provides several method specs to perform CRUD on Tyre entity.
 * @author VISHAL
 *
 */
public interface TyreService {

	/**
	 * Get All Tyre entities
	 * @return List<Tyre>
	 */
	public List<Tyre> getAllTyres();
	
	/**
	 * Save a Tyre entity
	 * @param tyres
	 * @return List<Tyre>
	 */
	public List<Tyre> save(List<Tyre> tyres);

	/**
	 * Find a Tyre Entity by Id
	 * @param id
	 * @return Tyre
	 */
	public Tyre find(Long id);

	/**
	 * Update a Tyre entity
	 * @param tyre
	 * @param id
	 * @return Tyre
	 */
	public Tyre update(Tyre tyre, Long id);

	/**
	 * Delete a Tyre entity
	 * @param id
	 * @return void
	 */
	public boolean delete(Long id);

}

package com.springboot.app.tyre.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.tyre.model.Tyre;
import com.springboot.app.tyre.service.TyreService;

import io.swagger.annotations.ApiOperation;

/**
 * TyreController provides several methods to handle CRUD specific httpRequests.
 * 
 * 
 * @author VISHAL
 *
 */
@RestController
@RequestMapping("/api/v1/tyres")
public class TyreController {


	private TyreService tyresService;

	private TyreController(TyreService tyresService) {
		this.tyresService = tyresService;
	}

	/**
	 * Get All Tyre entities.
	 * @return ResponseEntity<List<Tyre>>
	 */
	@ApiOperation(value = "Get all tyres")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Tyre>> getAllTyres() {
		List<Tyre> tyres = this.tyresService.getAllTyres();

		return ResponseEntity.ok().body(tyres);

	}

	/**
	 * Get Tyre entity by Id
	 * @param Long id
	 * @return ResponseEntity<Tyre>
	 */
	@ApiOperation(value = "Get tyre using id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Tyre> getTyre(@PathVariable Long id) {

		Tyre addedTyre = this.tyresService.find(id);

		return ResponseEntity.ok().body(addedTyre);

	}

	/**
	 * Save Tyre entities
	 * @param List<Tyre> tyres
	 * @return ResponseEntity<List<Tyre>>
	 */
	@ApiOperation(value = "Save tyre")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<List<Tyre>> saveTyre(@RequestBody List<Tyre> tyres) {

		List<Tyre> addedTyres = this.tyresService.save(tyres);

		return ResponseEntity.ok().body(addedTyres);

	}

	/**
	 * Update a Tyre entity
	 * @param tyre
	 * @param id
	 * @return ResponseEntity<Tyre>
	 */
	@ApiOperation(value = "Update tyre for a given Id")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Tyre> updateTyre(@RequestBody Tyre tyre, @PathVariable Long id) {
		Tyre addedTyre = this.tyresService.update(tyre, id);

		return ResponseEntity.ok().body(addedTyre);

	}

	/**
	 * Delete a Tyre entity
	 * @param id
	 * @return void
	 */
	@ApiOperation(value = "Delete tyre for a given Id")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteTyre(@PathVariable Long id) {

		if (this.tyresService.delete(id))
			ResponseEntity.noContent().build();

	}

}

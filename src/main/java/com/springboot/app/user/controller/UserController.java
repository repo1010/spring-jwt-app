package com.springboot.app.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.app.user.model.AuthResponse;
import com.springboot.app.user.model.UserEntity;
import com.springboot.app.user.service.CustomeUserDetailsService;
import com.springboot.app.user.service.UserService;
import com.springboot.app.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

/**
 * UserController provides several methods to handle CRUD specific httpRequests.
 * It also provides login method to perform login.
 * 
 * @author VISHAL
 *
 */
@Controller
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomeUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtilToken;

	/**
	 * Perform login for a UserEntity
	 * 
	 * @param user
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@ApiOperation(value = "Login")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserEntity user) throws Exception {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		} catch (BadCredentialsException ex) {
			ex.printStackTrace();
			throw new AuthenticationCredentialsNotFoundException("Incorrcet username and password", ex);
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		if (user.getRole() != null && !userDetails.getAuthorities().stream()
				.filter(auth -> auth.getAuthority().substring(5).equals(user.getRole())).findFirst().isPresent())
			throw new AuthenticationException("Input role do not match with given credential.");

		String jwt = jwtUtilToken.generateToken(userDetails);

		return ResponseEntity.ok().body(new AuthResponse(jwt));

	}

	/**
	 * Find all UserEntity entities.
	 * 
	 * @return ResponseEntity<List<UserEntity>>
	 */
	@ApiOperation(value = "Find all users")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> findAllUsers() {
		List<UserEntity> users = userService.findAll();
		return ResponseEntity.ok().body(users);
	}

	/**
	 * Find a UserEntity by id
	 * 
	 * @param id
	 * @return ResponseEntity<UserEntity>
	 */
	@ApiOperation(value = "Find user for a given Id")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserEntity> findUser(@PathVariable long id) {
		UserEntity user = userService.find(id).get();
		return ResponseEntity.ok().body(user);
	}

	/**
	 * Save a list of entities of tyre UserEntity .
	 * 
	 * @param users
	 * @return ResponseEntity<List<UserEntity>>
	 */
	@ApiOperation(value = "Save a user")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<List<UserEntity>> saveUser(@Valid @RequestBody List<UserEntity> users) {
		List<UserEntity> retndUsers = userService.save(users);
		return ResponseEntity.ok().body(retndUsers);
	}

	/**
	 * Update a UserEntity entity
	 * 
	 * @param user
	 * @param id
	 * @return ResponseEntity<UserEntity>
	 */
	@ApiOperation(value = "Update user for a given Id")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserEntity> updateUser(@Valid @RequestBody UserEntity user, @PathVariable long id) {

		UserEntity retndUser = userService.update(user, id);
		return ResponseEntity.ok().body(retndUser);
	}

	/**
	 * Delete a UserEntity entity.
	 * 
	 * @param id
	 * @return void
	 */
	@ApiOperation(value = "Delete a user for a given Id")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable long id) {

		userService.delete(id);
		ResponseEntity.noContent().build();
	}

}

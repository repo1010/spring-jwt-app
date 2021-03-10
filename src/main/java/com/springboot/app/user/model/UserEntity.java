package com.springboot.app.user.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.app.tyre.model.Tyre;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a User Entity")
@Entity
@Table(name = "USERS")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Unique identifier of the UserEntity. No two users can have the same id.", example = "1", required = true, position = 0)
	private long id;

	@ApiModelProperty(notes = "Username of the the User.", example = "user", required = true, position = 1)
	@NotNull(message = "Username can not be null")
	@NotBlank(message = "Username can not be blank")
	private String username;

	@ApiModelProperty(notes = "Password of the User.", example = "user", required = true, position = 2)
	@NotNull(message = "Password can not be null")
	@NotBlank(message = "Password can not be blank")
	private String password;

	@ApiModelProperty(notes = "Role of the User.", example = "USER", required = true, position = 3)
	@NotNull(message = "Role can not be null")
	@NotBlank(message = "Role can not be blank")
	private String role;

	private Set<Tyre> tyre = new HashSet<>();

	public UserEntity() {

	}

	public UserEntity(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	@Column(unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, targetEntity = Tyre.class)
	public Set<Tyre> getTyre() {
		return this.tyre;
	}

	public void setTyre(Set<Tyre> tyre) {
		this.tyre = tyre;
	}

}

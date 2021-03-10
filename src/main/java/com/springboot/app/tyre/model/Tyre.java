package com.springboot.app.tyre.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.app.user.model.UserEntity;

@Entity
@Table(name = "TYRES")
public class Tyre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotNull(message = "Username can not be null.")
	@NotBlank(message = "Username can not be blank.")
	private String username;

	private UserEntity user;

	@NotNull(message = "MonthDetail can not be null.")
	@NotBlank(message = "MonthDetail can not be Blank.")
	private String monthDtl;

	@NotNull(message = "YearDetail can not be null.")
	private int yearDtl;

	private int twoWhlrTyresCnt;

	private int fourWhlrTyresCnt;

	// This filed will be calculated based on twoWhlrTyresCnt and fourWhlrTyresCnt
	// field value
	private int totaTyresCnt;

	// Always updated as current date and time
	private LocalDateTime lastUpdated;

	public Tyre() {

	}

	public Tyre(String username, String monthDtl, int yearDtl, int twoWhlrTyresCnt, int fourWhlrTyresCnt,
			UserEntity userEntity) {
		super();
		this.username = username;
		this.monthDtl = monthDtl;
		this.yearDtl = yearDtl;
		this.twoWhlrTyresCnt = twoWhlrTyresCnt;
		this.fourWhlrTyresCnt = fourWhlrTyresCnt;
		this.user = userEntity;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMonthDtl() {
		return monthDtl;
	}

	public void setMonthDtl(String monthDtl) {
		this.monthDtl = monthDtl;
	}

	public int getYearDtl() {
		return yearDtl;
	}

	public void setYearDtl(int yearDtl) {
		this.yearDtl = yearDtl;
	}

	public int getTwoWhlrTyresCnt() {
		return twoWhlrTyresCnt;
	}

	public void setTwoWhlrTyresCnt(int twoWhlrTyresCnt) {
		this.twoWhlrTyresCnt = twoWhlrTyresCnt;
	}

	public int getFourWhlrTyresCnt() {
		return fourWhlrTyresCnt;
	}

	public void setFourWhlrTyresCnt(int fourWhlrTyresCnt) {
		this.fourWhlrTyresCnt = fourWhlrTyresCnt;
	}

	public int getTotaTyresCnt() {
		return totaTyresCnt;
	}

	public void setTotaTyresCnt(int i) {
		this.totaTyresCnt = this.twoWhlrTyresCnt + this.fourWhlrTyresCnt;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@PrePersist
	@PreUpdate
	public void lastUpdated() {
		this.setLastUpdated(LocalDateTime.now());
		this.setTotaTyresCnt(1);
	}

}

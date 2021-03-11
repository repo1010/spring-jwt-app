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
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.app.user.model.UserEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a Tyre entity")
@Entity
@Table(name = "TYRES")
public class Tyre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "id", example = "1", required = false, position = 0)
	private Long id;

	@ApiModelProperty(notes = "username", example = "username", required = true, position = 1)
	private String username;

	private UserEntity user;

	@ApiModelProperty(notes = "month detail", example = "JAN", required = true, position = 2)
	@NotNull(message = "MonthDetail can not be null.")
	@NotBlank(message = "MonthDetail can not be Blank.")
	@Pattern(regexp = "JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Month detail is not valid")
	private String monthDtl;

	@ApiModelProperty(notes = "year detail", example = "2021", required = true, position = 3)
	@NotNull(message = "Year detail can not be null.")
	@Range(min = 1900, max = 2100, message = "Year deatil should be between 1900 and 2100")
	private int yearDtl;

	@ApiModelProperty(notes = "2WTyres count", example = "130", required = false, position = 4)
	private int twoWhlrTyresCnt;

	@ApiModelProperty(notes = "4WTyres count", example = "200", required = false, position = 5)
	private int fourWhlrTyresCnt;

	// This filed will be calculated based on twoWhlrTyresCnt and fourWhlrTyresCnt
	// field value
	@ApiModelProperty(notes = "Total tyres count", example = "330", required = false, position = 6)
	private int totaTyresCnt;

	// Always updated as current date and time
	@ApiModelProperty(notes = "Last updated", example = "2021-03-11T12:23:17.189", required = false, position = 7)
	private LocalDateTime lastUpdated;

	public Tyre() {

	}

	public Tyre(String username, String monthDtl, int yearDtl, int twoWhlrTyresCnt, int fourWhlrTyresCnt,
			UserEntity userEntity) {
		super();
		this.username = username;
		this.monthDtl = monthDtl.toUpperCase();
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
		return monthDtl.toUpperCase();
	}

	public void setMonthDtl(String monthDtl) {
		this.monthDtl = monthDtl.toUpperCase();
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

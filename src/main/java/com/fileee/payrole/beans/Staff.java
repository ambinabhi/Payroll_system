package com.fileee.payrole.beans;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "staff", uniqueConstraints = @UniqueConstraint(columnNames = { "STAFF_ID" }))
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STAFF_ID")
	private Integer staffId;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "IS_HOURLY")
	private Boolean isHourly = false;

	@Column(name = "WAGE")
	private Integer wage;
	
	@OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worklog> worklogs;

	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsHourly() {
		return isHourly;
	}

	public void setIsHourly(Boolean isHourly) {
		this.isHourly = isHourly;
	}

	public Integer getWage() {
		return wage;
	}

	public void setWage(Integer wage) {
		this.wage = wage;
	}
	
}
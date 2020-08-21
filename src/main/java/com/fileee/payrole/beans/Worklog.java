package com.fileee.payrole.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "worklog")
public class Worklog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WORKLOG_ID")
	private Integer worklogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="STAFF_ID")
    private Staff staff;

	@Column(name = "WORKING_HOURS")
	private Integer workingHours;

	public Integer getWorklogId() {
		return worklogId;
	}

	public void setWorklogId(Integer worklogId) {
		this.worklogId = worklogId;
	}

	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "WORKING_DATE")
	private Date workingDate;

	public Integer getWorkingHours() {
		return workingHours;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public void setWorkingHours(Integer workingHours) {
		this.workingHours = workingHours;
	}

	public Date getWorkingDate() {
		return workingDate;
	}

	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}

}

package com.fileee.payrole.beans;

public class Payrole {

	private Integer staffId;
	private float wage;
	private String duration;
	private Integer totalHours;
	private String staffName;
	private String emailId;
	
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
	public float getWage() {
		return wage;
	}
	public void setWage(float wage) {
		this.wage = wage;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Integer getTotalHours() {
		return totalHours;
	}
	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}

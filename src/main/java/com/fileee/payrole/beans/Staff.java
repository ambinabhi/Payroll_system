package com.fileee.payrole.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "staff", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}) )
public class Staff  {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String name;

  private String email;
  
  private Boolean isHourly=false;
  
  private Integer wage;
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
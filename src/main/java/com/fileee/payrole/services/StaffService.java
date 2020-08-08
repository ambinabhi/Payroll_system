package com.fileee.payrole.services;

import java.util.List;
import java.util.Optional;

import com.fileee.payrole.beans.Staff;

public interface StaffService {
	
	Staff save(Staff staff);
	
	void delete(Staff staff);
	
	void deleteAll();
	
	List<Staff> findAllStaff();
	
	Optional<Staff> findOneById(Integer staffId);
	
	Boolean isExistsById(Integer staffId);
}

package com.fileee.payrole.services;

import java.util.Date;
import java.util.List;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;

public interface WorklogService {
		
	void save(Worklog worklog);
	
	List<Worklog> findWorklogsByStaff(Staff staff);
	
	List<Worklog> findWorklogByStaffInDateRange(Date fromDate, Date toDate, Staff staff);
	
}

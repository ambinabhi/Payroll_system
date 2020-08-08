package com.fileee.payrole.services;

import java.util.Date;
import java.util.List;

import com.fileee.payrole.beans.Worklog;

public interface WorklogService {
		
	void save(Worklog worklog);
	
	List<Worklog> findWorklogByIdInDateRange(Date fromDate, Date toData, Integer staffId);
}

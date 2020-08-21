package com.fileee.payrole.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.repository.WorklogRepository;

@Service
@Primary
public class WorklogServiceImpl implements WorklogService {
	
	@Autowired
	private WorklogRepository worklogRepository;

	@Override
	public void save(Worklog worklog) {
		worklogRepository.save(worklog);
	}

	@Override
	public List<Worklog> findWorklogsByStaff(Staff staff) {
		
		return (List<Worklog>) worklogRepository.findWorklogsByStaff(staff);
	}

	@Override
	public List<Worklog> findWorklogByStaffInDateRange(Date fromDate, Date toDate, Staff staff) {
		return (List<Worklog>) worklogRepository.findWorklogByStaffInDateRange(fromDate, toDate, staff);
	}

	


}

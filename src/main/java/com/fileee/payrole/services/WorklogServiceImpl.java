package com.fileee.payrole.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.repository.WorklogRepository;

@Service
@Primary
public class WorklogServiceImpl implements WorklogService {
	
	@Autowired
	private WorklogRepository worklogRepository;

	@Override
	public void save(Worklog worklog) {
		// TODO Auto-generated method stub
		worklogRepository.save(worklog);
	}

	@Override
	public List<Worklog> findWorklogByIdInDateRange(Date fromDate, Date toDate, Integer staffId) {
		//return (List<Worklog>) worklogRepository.findWorklogByIdInDateRange(fromDate, toDate, staffId);
		
		return (List<Worklog>) worklogRepository.findAll();
	}

}

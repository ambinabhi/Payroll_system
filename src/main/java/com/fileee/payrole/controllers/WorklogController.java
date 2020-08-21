package com.fileee.payrole.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fileee.payrole.beans.Payrole;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.exception.StaffNotFoundException;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;
import com.fileee.payrole.utils.ConstantUtils;
import com.fileee.payrole.utils.DateUtils;
import com.fileee.payrole.utils.PayroleUtils;


@Controller
public class WorklogController {
	
	Logger logger = LoggerFactory.getLogger(WorklogController.class);
	
	@Autowired
	private WorklogService worklogService;

	@Autowired
	private StaffService staffService;

	@PostMapping(path = "/worklog/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // Requests
	public ResponseEntity<String> addNewWorklog(@PathVariable Integer staffId, @RequestBody Worklog worklog) {

		logger.info(ConstantUtils.ADD_NEW_WORKLOG + staffId);
		
	
			final Staff staff = staffService.findOneById(staffId)
					.orElseThrow(() -> new StaffNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
			
			worklog.setStaff(staff);
			worklogService.save(worklog);
			
			JSONObject json = new JSONObject();
			json.put("message", "Worklog saved succesfully");
			return new ResponseEntity<>(json.toString(), HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/worklog/{staffId}")
	public ResponseEntity<Payrole> getAllWorklogsByStaff(@PathVariable Integer staffId, 
			@RequestParam("from_date") String fromDate, @RequestParam("to_date") String toDate) {
		
		logger.info(ConstantUtils.GET_WORKLOG_BY_RANGE + staffId);
		
		final Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new StaffNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
		
		List<Worklog> allWorkLogs = new ArrayList<Worklog>();
		
		if(fromDate.isEmpty() || toDate.isEmpty()) {
			logger.info("No Dates Found: Getting All Worklog by Staff");
			allWorkLogs = worklogService.findWorklogsByStaff(staff);
		
		}else {
			logger.info("Dates Found: Getting Worklog in Date Range");
			Date startDate = DateUtils.getFormattedDate(fromDate);
			Date endDate = DateUtils.getFormattedDate(toDate);
			
			allWorkLogs = worklogService.findWorklogByStaffInDateRange(startDate, endDate, staff);
		}
		
		if(allWorkLogs.size() > 0) {
			Payrole payrole = PayroleUtils.getPayroleForStaff(staff, allWorkLogs);
			return new ResponseEntity<>(payrole, HttpStatus.OK);
		}else {
			throw new ResourceNotFoundException("No worklogs found for this staff");
		}
	}
}

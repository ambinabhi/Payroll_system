package com.fileee.payrole.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fileee.payrole.beans.Payrole;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;
import com.fileee.payrole.utils.ConstantUtils;
import com.fileee.payrole.utils.DateUtils;
import com.fileee.payrole.utils.PDFUtils;
import com.fileee.payrole.utils.PayroleUtils;
import com.lowagie.text.DocumentException;


@Controller
public class WorklogController {
	
	Logger logger = LoggerFactory.getLogger(WorklogController.class);
	
	@Autowired
	private WorklogService worklogService;

	@Autowired
	private StaffService staffService;

	@PostMapping(path = "/worklog/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // Requests
	public ResponseEntity<Worklog> addNewWorklog(@PathVariable Integer staffId, @RequestBody Worklog worklog) {

		logger.info(ConstantUtils.ADD_NEW_WORKLOG + staffId);
		
	
			final Staff staff = staffService.findOneById(staffId)
					.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
			
			worklog.setStaff(staff);			
			worklog = worklogService.save(worklog);
			
			return new ResponseEntity<>(worklog, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/worklog", params = "staffId")
	public ResponseEntity<List<Worklog>> getAllWorklogsByStaff(@RequestParam Integer staffId) {
		logger.info(ConstantUtils.GET_ALL_STAFF);
		
		final Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
		
		return new ResponseEntity<>(worklogService.findWorklogsByStaff(staff), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/worklog", params = { "staffId", "fromDate","toDate"})
	public ResponseEntity<Payrole> getAllWorklogsByStaffInDateRange(@RequestParam Integer staffId, 
			@RequestParam String fromDate, @RequestParam String toDate) {
		
		logger.info(ConstantUtils.GET_WORKLOG_BY_RANGE + staffId);
		
		final Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
		
		List<Worklog> allWorkLogs = new ArrayList<Worklog>();
		
		if(fromDate.isEmpty() || toDate.isEmpty()) {
			logger.info("No Dates Found: Getting All Worklog by Staff");
			throw new ResourceNotFoundException("Missing Date Ranges");
		
		}else {
			logger.info("Dates Found: Getting Worklog in Date Range");
			Date startDate = DateUtils.getFormattedDate(fromDate);
			Date endDate = DateUtils.getFormattedDate(toDate);
			
			allWorkLogs = worklogService.findWorklogByStaffInDateRange(startDate, endDate, staff);
		}
		
		if(allWorkLogs.size() > 0) {
			Payrole payrole = PayroleUtils.getPayroleForStaff(staff, allWorkLogs);
			try {
				PDFUtils.generatePayrolePDF(payrole);
			} catch (IOException e) {
				logger.error(e.getLocalizedMessage());
			} catch (DocumentException e) {
				logger.error(e.getLocalizedMessage());
			}
			return new ResponseEntity<>(payrole, HttpStatus.OK);
		}else {
			throw new ResourceNotFoundException("No worklogs found for this staff");
		}
	}
}

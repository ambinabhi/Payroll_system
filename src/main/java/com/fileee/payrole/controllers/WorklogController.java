package com.fileee.payrole.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fileee.payrole.beans.Payrole;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.services.WorklogService;
import com.fileee.payrole.util.Utils;


@Controller
@RequestMapping(path = "/worklog")
public class WorklogController {
	
	private static String TAG = "DEBUG::WorklogController";
	
	@Autowired
	private WorklogService worklogService;

	@Autowired
	private StaffService staffService;

	@PostMapping(path = "/new/{staffId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // Requests
	public ResponseEntity<String> addNewWorklog(@PathVariable Integer staffId, @RequestBody Worklog worklog) {

		System.out.println(TAG + " addNewWorklog " + staffId);
		
		if(staffService.isExistsById(staffId)) {
			final Staff staff = staffService.findOneById(staffId)
					.orElseThrow(() -> new ResourceNotFoundException("Staff not found"));
			worklog.setStaff(staff);
			worklogService.save(worklog);
			
			JSONObject json = new JSONObject();
			json.put("message", "Worklog saved succesfully");
			return new ResponseEntity<>(json.toString(), HttpStatus.CREATED);
		}else {
			JSONObject json = new JSONObject();
			json.put("message", "Staff Not Found");
			return new ResponseEntity<>(json.toString(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/view/{staffId}")
	public ResponseEntity<Payrole> getWorklogByIdInRange(@PathVariable Integer staffId, @RequestBody String dateRange) {
		
		System.out.println(TAG + " getWorklogByIdInRange");
		
		JSONObject dateObject = new JSONObject(dateRange);
		final Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff not found"));

		Integer totalWorkingHours = 0;
		float totalWage = 0;
		Date fromDate = Utils.getFormattedDate(dateObject.getString("fromDate"));
		Date toDate = Utils.getFormattedDate(dateObject.getString("toDate"));
		
		if (staff.getIsHourly()) {
			List<Worklog> logs = worklogService.findWorklogByIdInDateRange(fromDate, toDate, staffId);
			for (Worklog worklog : logs) {
				totalWorkingHours = totalWorkingHours + worklog.getWorkingHours();
			}

			totalWage = totalWorkingHours * staff.getWage();
		} else {
			LocalDate fromLocaleDate = LocalDate.parse(dateObject.getString("fromDate"));
			LocalDate toLocaleDate = LocalDate.parse(dateObject.getString("toDate"));
			long noOfDays = ChronoUnit.DAYS.between(fromLocaleDate, toLocaleDate);
			totalWorkingHours = (int) (noOfDays * 8);
			totalWage = ((float)noOfDays / 30) * staff.getWage();
		}

		Payrole payrole = new Payrole();
		payrole.setDuration("" + fromDate + " - " + toDate);
		payrole.setEmailId(staff.getEmail());
		payrole.setStaffId(staffId);
		payrole.setStaffName(staff.getName());
		payrole.setTotalHours(totalWorkingHours);
		payrole.setWage(totalWage);

		return new ResponseEntity<>(payrole, HttpStatus.OK);
	}
}

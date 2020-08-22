package com.fileee.payrole.controllers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.services.PatchService;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.utils.ConstantUtils;

@Controller
public class StaffMemberController {

	Logger logger = LoggerFactory.getLogger(StaffMemberController.class);

	@Autowired
	private StaffService staffService;

	@Autowired
	private PatchService patchService;
	

	@PostMapping(path = "/staff", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // Requests
	public ResponseEntity<Staff> addNewStaff(@RequestBody Staff staff) {

		logger.info(ConstantUtils.ADD_NEW_STAFF);

		staff = staffService.save(staff);

		return new ResponseEntity<>(staff, HttpStatus.CREATED);

	}

	@GetMapping(path = "/staff/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Staff> getStaffMember(@PathVariable("id") Integer staffId) {

		logger.info(ConstantUtils.GET_STAFF + staffId);

		Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}

	@GetMapping(path = "/staff")
	public ResponseEntity<List<Staff>> getAllStaff() {

		logger.info(ConstantUtils.GET_ALL_STAFF);

		return new ResponseEntity<>(staffService.findAllStaff(), HttpStatus.OK);

	}

	@DeleteMapping(path = "/staff/{id}")
	public ResponseEntity<String> deleteStaffMember(@PathVariable("id") Integer staffId) {

		logger.info(ConstantUtils.DELETE_STAFF + staffId);

		Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));

		staffService.delete(staff);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/staff/{id}")
	public ResponseEntity<Staff> updateStaff(@PathVariable("id") Integer staffId, @RequestBody Staff staff) {

		logger.info(ConstantUtils.UPDATE_STAFF + staffId);

		Staff staffCurrent = staffService.findOneById(staffId).map(staffDb -> {

			staffDb.setEmail(staff.getEmail());
			staffDb.setIsHourly(staff.getIsHourly());
			staffDb.setName(staff.getName());
			staffDb.setWage(staff.getWage());
			return staffDb;
		}).orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));

		staffCurrent = staffService.save(staffCurrent);
		return new ResponseEntity<>(staffCurrent, HttpStatus.OK);
	}

	@PatchMapping(path = "/staff/{id}")
	public ResponseEntity<Staff> patchStaff(@PathVariable("id") Integer staffId, @RequestBody String properties) {

		logger.info(ConstantUtils.PATCH_STAFF + staffId);

		Staff staffCurrent = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Member with staffId: " + staffId + " Not Found"));
		try {
			logger.debug(ConstantUtils.PATCH_STAFF);
			if (!patchService.map(properties, staffCurrent)) {
				return new ResponseEntity<>(staffCurrent, HttpStatus.NOT_MODIFIED);
			}
		} catch (IOException e) {
			logger.error("Error !", e);
		}

		Staff patchedStaff = staffService.save(staffCurrent);
		return new ResponseEntity<>(patchedStaff, HttpStatus.OK);
	}

}

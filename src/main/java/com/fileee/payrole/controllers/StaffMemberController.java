package com.fileee.payrole.controllers;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.exception.ResourceNotFoundException;
import com.fileee.payrole.services.StaffService;
import com.fileee.payrole.util.Utils;
import com.fileee.payrole.services.MapperService;

@Controller
@RequestMapping(path = "/staff")
public class StaffMemberController {
	
	private static String TAG = "DEBUG::StaffMemberController";
	
	@Autowired
	private StaffService staffService;
	
	@Autowired 
	private MapperService mapperService;
	
	
	@PostMapping(path = "/new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE) // Requests
	public ResponseEntity<Staff> addNewStaff(@RequestBody Staff staff) {

		System.out.println(TAG + " addNewStaff");

		staff = staffService.save(staff);
		
		return new ResponseEntity<>(staff, HttpStatus.CREATED);

	}

	@GetMapping(path = "/list")
	public ResponseEntity<List<Staff>> getAllStaff() {

		System.out.println(TAG + " getAllStaff");
		return new ResponseEntity<>(Utils.sortStaffMembers(staffService.findAllStaff()), HttpStatus.OK);
	}

	@GetMapping(path = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Staff> getStaffMember(@PathVariable("id") Integer staffId) {

		System.out.println(TAG + " getStaffMember" + staffId);
		
		Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		return new ResponseEntity<>(staff, HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteStaffMember(@PathVariable("id") Integer staffId) {

		System.out.println(TAG + " deleteStaffMember");
		
		Staff staff = staffService.findOneById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		staffService.delete(staff);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(path = "/deleteAll")
	public ResponseEntity<String> deleteAllStaff() {
		
		System.out.println(TAG + " deleteAllStaff");
		
		staffService.deleteAll();
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/edit/{id}")
	public ResponseEntity<Staff> updateStaff(@PathVariable Integer staffId, @RequestBody Staff staff) {
		
		System.out.println(TAG + " updateStaff");
		
		Staff staffCurrent = staffService.findOneById(staffId).map(staffDb -> {

			staffDb.setEmail(staff.getEmail());
			staffDb.setIsHourly(staff.getIsHourly());
			staffDb.setName(staff.getName());
			staffDb.setWage(staff.getWage());
			return staffDb;
		}).orElseThrow(() -> new ResourceNotFoundException("Staff Not Found"));

		staffCurrent = staffService.save(staffCurrent);
		return new ResponseEntity<>(staffCurrent, HttpStatus.OK);
	}

	@PatchMapping(path = "/edit/{id}")
	public ResponseEntity<Staff> patchStaff(@PathVariable Integer id, @RequestBody String properties) {

		System.out.println(TAG + " patchStaff");

		Staff staffCurrent = staffService.findOneById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Staff Not Found"));
		try {
			if(!mapperService.map(properties, staffCurrent)) {
				return new ResponseEntity<>(staffCurrent, HttpStatus.NOT_MODIFIED);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Staff patchedStaff = staffService.save(staffCurrent);
		return new ResponseEntity<>(patchedStaff, HttpStatus.OK);
	}

}

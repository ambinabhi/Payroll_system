package com.fileee.payrole.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.repository.StaffRepository;

@Service
@Primary
public class StaffServiceImpl implements StaffService {
	
	@Autowired 
	private StaffRepository staffRepository;

	@Override
	public Staff save(Staff staff) {
		return staffRepository.save(staff);
	}

	@Override
	public void delete(Staff staff) {
		this.staffRepository.delete(staff);
	}

	@Override
	public void deleteAll() {
		this.staffRepository.deleteAll();
	}
	
	@Override
	public List<Staff> findAllStaff() {
		return staffRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
	}

	@Override
	public Optional<Staff> findOneById(Integer id) {
		return this.staffRepository.findById(id);
	}

	@Override
	public Boolean isExistsById(Integer staffId) {
		return this.staffRepository.existsById(staffId);
	}

}

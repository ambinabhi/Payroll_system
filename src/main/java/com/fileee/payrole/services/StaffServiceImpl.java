package com.fileee.payrole.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.repository.StaffRepository;

@Service
@Primary
public class StaffServiceImpl implements StaffService {
	
	@Autowired // This means to get the bean called userRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	private StaffRepository staffRepository;

	@Override
	public Staff save(Staff staff) {
		// TODO Auto-generated method stub
		return staffRepository.save(staff);
	}

	@Override
	public void delete(Staff staff) {
		// TODO Auto-generated method stub
		this.staffRepository.delete(staff);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		this.staffRepository.deleteAll();
	}
	
	@Override
	public List<Staff> findAllStaff() {
		// TODO Auto-generated method stub
		return (List<Staff>) staffRepository.findAll();
	}

	@Override
	public Optional<Staff> findOneById(Integer id) {
		// TODO Auto-generated method stub
		return this.staffRepository.findById(id);
	}

	@Override
	public Boolean isExistsById(Integer staffId) {
		return this.staffRepository.existsById(staffId);
	}

}

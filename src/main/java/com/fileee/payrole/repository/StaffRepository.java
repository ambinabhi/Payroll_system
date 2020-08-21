package com.fileee.payrole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileee.payrole.beans.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
	
}


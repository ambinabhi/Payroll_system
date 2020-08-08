package com.fileee.payrole.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fileee.payrole.beans.Staff;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {

}


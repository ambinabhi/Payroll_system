package com.fileee.payrole.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fileee.payrole.beans.Worklog;

@Repository
public interface WorklogRepository extends CrudRepository<Worklog, Integer> {

//	@Query(value = "select * from Worklog w WHERE (w.workingDate BETWEEN :fromDate AND :toDate)")
//	List<Worklog> findWorklogByIdInDateRange(Date fromDate, Date toDate, Integer staffId);
		
}

package com.fileee.payrole.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fileee.payrole.beans.Worklog;

@Repository
public interface WorklogRepository extends CrudRepository<Worklog, Integer> {

	@Query(value = "select w from Worklog w WHERE (w.workingDate BETWEEN :fromDate AND :toDate) AND w.staffId = :staffId")
	List<Worklog> findWorklogByIdInDateRange(Date fromDate, Date toDate, Integer staffId);
		
}

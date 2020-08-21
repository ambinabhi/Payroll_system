package com.fileee.payrole.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;

@Repository
public interface WorklogRepository extends JpaRepository<Worklog, Long>{

	@Query(value = "select w from Worklog w WHERE (w.workingDate BETWEEN :fromDate AND :toDate) AND w.staff = :staff ORDER BY w.workingDate")
	List<Worklog> findWorklogByStaffInDateRange(Date fromDate, Date toDate, Staff staff);
		
	@Query(value = "select w from Worklog w WHERE w.staff = :staff ORDER BY w.workingDate")
	List<Worklog> findWorklogsByStaff(Staff staff);
}

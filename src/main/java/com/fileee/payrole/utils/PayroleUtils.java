package com.fileee.payrole.utils;

import java.util.List;

import com.fileee.payrole.beans.Payrole;
import com.fileee.payrole.beans.Staff;
import com.fileee.payrole.beans.Worklog;

public class PayroleUtils {

	public static Payrole getPayroleForStaff(Staff staff, List<Worklog> allWorkLogs) {
		Integer totalWorkingHours = 0;
		float totalWage = 0;

		if (staff.getIsHourly()) {
			for (Worklog worklog : allWorkLogs) {
				totalWorkingHours = totalWorkingHours + worklog.getWorkingHours();
			}
			totalWage = totalWorkingHours * staff.getWage();
		} else {
			long noOfDays = allWorkLogs.size();
			totalWorkingHours = (int) (noOfDays * 8);
			totalWage = ((float)noOfDays / 30) * staff.getWage();
		}
		
		Payrole payrole = new Payrole();
		payrole.setDuration("" + allWorkLogs.get(0).getWorkingDate() + " - " + allWorkLogs.get(allWorkLogs.size()-1).getWorkingDate());
		payrole.setEmailId(staff.getEmail());
		if(staff.getIsHourly()) {
			payrole.setSalaryType("Hourly Salaried Employee");
		}else {
			payrole.setSalaryType("Fixed Salaried Employee");
		}
		payrole.setStaffId(staff.getStaffId());
		payrole.setStaffName(staff.getName());
		payrole.setTotalHours(totalWorkingHours);
		payrole.setWage(totalWage);
		
		return payrole;
	}

}

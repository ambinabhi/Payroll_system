package com.fileee.payrole.utils;

import java.util.List;

import com.fileee.payrole.beans.Staff;

public class SortUtils {
	
	public static List<Staff> sortStaffMembers(List<Staff> staffs) {
		boolean sortFlag = true;
		Staff temp;
		while (sortFlag) {
			sortFlag = false;
			for (int count = 0; count < staffs.size() - 1; count++) {
				if (staffs.get(count).getName().compareToIgnoreCase(staffs.get(count + 1).getName()) > 0) {
					temp = staffs.get(count);
					staffs.set(count, staffs.get(count + 1));
					staffs.set(count + 1, temp);
					sortFlag = true;
				}
			}
		}
		return staffs;
	}
}

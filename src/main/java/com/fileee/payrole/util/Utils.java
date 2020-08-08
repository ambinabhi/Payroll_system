package com.fileee.payrole.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fileee.payrole.beans.Staff;

public class Utils {
	
	public static Date getFormattedDate(String dateString) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<Staff> sortStaffMembers(List<Staff> staffs) {
	        boolean sortFlag = true;
	        Staff temp;
	        while(sortFlag) {
	         sortFlag = false;
	         for (int count = 0; count < staffs.size()-1; count ++) {
	             if (staffs.get(count).getName().compareToIgnoreCase(staffs.get(count+1).getName()) > 0) {
	                 temp = staffs.get(count); 
	                 staffs.set(count, staffs.get(count+1));
	                 staffs.set(count+1, temp);
	                 sortFlag = true;
	             }
	         }
	     }
	        return staffs;
	   }

}

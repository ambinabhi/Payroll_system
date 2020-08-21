package com.fileee.payrole.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PayroleUtils.class);
	
	public static Date getFormattedDate(String dateString) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		try {
			return dateFormat.parse(dateString);
		} catch (ParseException e) {
			logger.error("Error Date Format ", e);
		}
		return null;
	}

}

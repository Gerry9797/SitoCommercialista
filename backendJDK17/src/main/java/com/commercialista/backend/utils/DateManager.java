package com.commercialista.backend.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class DateManager {
	
	public static LocalDate convertJavaUtilDateToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
	}
	
	public static long getDayDifference(Date firstDate, Date secondDate) {
		LocalDate date1 = convertJavaUtilDateToLocalDate(firstDate);
		LocalDate date2 = convertJavaUtilDateToLocalDate(secondDate);
		long nOfDaysBetween = ChronoUnit.DAYS.between(date1, date2);
		return nOfDaysBetween;
	}
	
	public static Date addDaysToDate(Date date, int days) {
		return DateUtils.addDays(date, days);
	}
	
	public static Date addHoursToJavaUtilDate(Date date, int hours) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.HOUR_OF_DAY, hours);
	    return calendar.getTime();
	}
	
	public static String convertDateToItalianFormatString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ITALY);  
        String strDate = dateFormat.format(date);  
        return strDate;
	}

}
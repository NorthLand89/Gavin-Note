package com.example.eventeverytime.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtility {
public static Date longToDate(long timeLong){
	Date date = new Date(timeLong);
	return date;
}
public static String longToString(long timeLong){
//	
//	Calendar calendar = Calendar.getInstance();
//	calendar.setTime(date)
//	
//	
	
	
	String timeString;
	Date date = longToDate(timeLong);
	SimpleDateFormat formater=new SimpleDateFormat("yyyy/MM/dd  HH:mm");
	timeString=formater.format(date);
	return timeString;
}

public static String LongStringToDateString(String longTime){
	long time = Long.parseLong(longTime);
	return longToString(time);
}
}

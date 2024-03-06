package com.nutrimate.nutrimatebackend;

import java.sql.Date;
import java.time.LocalDate;


public class TimeTest {
	
	
	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		System.out.println(date);
		LocalDate localDate = date.toLocalDate();
		localDate = localDate.plusDays(-6);
		date = Date.valueOf(localDate);
		System.out.println(date);
	}
}

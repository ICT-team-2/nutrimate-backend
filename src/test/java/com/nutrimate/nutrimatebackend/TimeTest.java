package com.nutrimate.nutrimatebackend;

import com.nutrimate.nutrimatebackend.model.record.AnalyzeDateDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;


public class TimeTest {
	
	public static void main(String[] args) {
//		Date date = new Date(System.currentTimeMillis());
//		System.out.println(date);
//		LocalDate localDate = date.toLocalDate();
//		localDate = localDate.plusDays(-6);
//		date = Date.valueOf(localDate);
		AnalyzeDateDto dto = new AnalyzeDateDto();
		
		String endDate = LocalDate.now().toString();
		
		List<AnalyzeDateDto.PeriodDto> period = setDayPeriod(endDate, 7);
		
		System.out.println(period);
		
		System.out.println(LocalDate.parse("2022-01-01").
				plusDays(1));
	}
	
	public static List<AnalyzeDateDto.PeriodDto> setDayPeriod(String endDate, int periodCount) {
		List<AnalyzeDateDto.PeriodDto> period = IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusDays(i).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusDays(i).toString());
			return periodDto;
		}).toList();
		return period;
	}
	public static List<AnalyzeDateDto.PeriodDto> setWeekPeriod(String endDate, int periodCount) {
		List<AnalyzeDateDto.PeriodDto> period = IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusDays(i * 7 + 6).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusDays(i * 7).toString());
			return periodDto;
		}).toList();
		return period;
	}
	
	public static List<AnalyzeDateDto.PeriodDto> setMonthPeriod(String endDate, int periodCount) {
		List<AnalyzeDateDto.PeriodDto> period = IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusMonths(i).withDayOfMonth(1).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusMonths(i).withDayOfMonth(LocalDate.parse(endDate).minusMonths(i).lengthOfMonth()).toString());
			return periodDto;
		}).toList();
		return period;
	}
}

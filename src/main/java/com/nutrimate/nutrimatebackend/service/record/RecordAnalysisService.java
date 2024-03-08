package com.nutrimate.nutrimatebackend.service.record;

import com.nutrimate.nutrimatebackend.mapper.record.RecordAnalysisMapper;
import com.nutrimate.nutrimatebackend.model.record.AnalyzeDateDto;
import com.nutrimate.nutrimatebackend.model.record.RecordAnalysisDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class RecordAnalysisService {
	private RecordAnalysisMapper recordAnalysisMapper;
	
	public RecordAnalysisService(RecordAnalysisMapper recordAnalysisMapper) {
		this.recordAnalysisMapper = recordAnalysisMapper;
	}
	public static List<AnalyzeDateDto.PeriodDto> setDayPeriod(String endDate, int periodCount) {
		return IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusDays(i).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusDays(i).toString());
			return periodDto;
		}).toList();
	}
	
	public static List<AnalyzeDateDto.PeriodDto> setWeekPeriod(String endDate, int periodCount) {
		return IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusDays(i * 7 + 6).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusDays(i * 7).toString());
			return periodDto;
		}).toList();
	}
	
	public static List<AnalyzeDateDto.PeriodDto> setMonthPeriod(String endDate, int periodCount) {
		return IntStream.range(0, periodCount).mapToObj(i -> {
			AnalyzeDateDto.PeriodDto periodDto = new AnalyzeDateDto.PeriodDto();
			periodDto.setStartDate(LocalDate.parse(endDate).minusMonths(i).withDayOfMonth(1).toString());
			periodDto.setEndDate(LocalDate.parse(endDate).minusMonths(i).withDayOfMonth(LocalDate.parse(endDate).minusMonths(i).lengthOfMonth()).toString());
			return periodDto;
		}).toList();
	}
	public List<RecordAnalysisDto> findRecordAnalysis(AnalyzeDateDto dateDto) {
		return recordAnalysisMapper.findRecordAnalysis(dateDto);
	}
	
	public List<RecordAnalysisDto> findRecordAnalysisForGraph(AnalyzeDateDto dateDto) {
		switch (dateDto.getPeriodType()) {
			case DAY:
				dateDto.setPeriod(setDayPeriod(dateDto.getEndDate(), dateDto.getPeriodCount()));
				break;
			case WEEK:
				dateDto.setPeriod(setWeekPeriod(dateDto.getEndDate(), dateDto.getPeriodCount()));
				break;
			case MONTH:
				dateDto.setPeriod(setMonthPeriod(dateDto.getEndDate(), dateDto.getPeriodCount()));
				break;
		}
		return recordAnalysisMapper.findRecordAnalysisForGraph(dateDto);
	}
}

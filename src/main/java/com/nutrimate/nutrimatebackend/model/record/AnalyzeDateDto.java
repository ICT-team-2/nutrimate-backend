package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AnalyzeDateDto")
public class AnalyzeDateDto {
	int userId;
	
	String doDate;
	
	// 기간 설정(그래프용)
	String endDate;
	PeriodType periodType;
	int periodCount;
	
	List<PeriodDto> period;
	
	public enum PeriodType {
		DAY, WEEK, MONTH
	}
	
	@Getter
	@Setter
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	@Alias("PeriodDto")
	public static class PeriodDto {
		String startDate;
		String endDate;
	}
}

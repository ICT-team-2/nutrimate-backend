package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("RecordAnalysisDto")
public class RecordAnalysisDto {
	
	List<SportRecordDto> sportList;
	List<FoodRecordDto> foodList;
	
	private int userId;//유저아이디
	private int totalSportTime;//오늘 운동한 총 시간
	private int totalSportCalorie;//오늘 소모한 총 칼로리
	
	private int totalDietCalorie;//오늘 섭취한 총 칼로리
	private int totalCarbo;//오늘 섭취한 총 탄수화물
	private int totalProtein;//오늘 섭취한 총 단백질
	private int totalProvi;//오늘 섭취한 총 지방
	
	private int recommendedCalorie; //추천칼로리
	private int recommendedCarbo; //추천탄수화물
	private int recommendedProtein;//추천단백질
	private int recommendedProvi;//추천지방
	
	private Date startDate;//시작날짜(그래프)
	private Date endDate;
	
	private String doDate;//기록 날짜
}

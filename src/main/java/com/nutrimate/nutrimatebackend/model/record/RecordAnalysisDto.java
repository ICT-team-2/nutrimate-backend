package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("RecordAnalysisDto")
public class RecordAnalysisDto {
	
	private int userId;//유저아이디
	private int totalSportTime;//오늘 운동한 총 시간
	private Double totalSportCal;//오늘 소모한 총 칼로리
	
	private Double totalDietCal;//오늘 섭취한 총 칼로리
	private Double totalCarbo;//오늘 섭취한 총 탄수화물
	private Double totalProtein;//오늘 섭취한 총 단백질
	private Double totalProvi;//오늘 섭취한 총 지방
	
	private Double recommendCal; //추천칼로리
	private Double recommendCarbo; //추천탄수화물
	private Double recommendProtein;//추천단백질
	private Double recommendProvi;//추천지방
	
	private String doDate;//기록 날짜
	private String startDate;//기록 시작 날짜
	private String endDate;//기록 끝 날짜
}

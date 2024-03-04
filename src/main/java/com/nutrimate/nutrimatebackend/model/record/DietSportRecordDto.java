package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("DietSportRecordDto")
public class DietSportRecordDto {
	private int userId; // 유저아이디
	private int recordId; // 기록아이디
	private int dietrecordId; // 식단기록아이디
	private int exerciseId; // 운동기록아이디
	
	private int foodId; // 음식아이디
	private String foodName; // 음식이름
	private double foodCal; // 음식칼로리
	private double userCal; // 일일권장칼로리
	private double foodCarbo; // 음식탄수화물
	private double foodProtein; // 음식단백질
	private double foodProvi; // 음식지방
	
	private int sportId; // 운동아이디
	private String sportName; // 운동이름
	private int sportMet; // Met
	private int sportTime; // 운동시간
	private int sportCal; // 운동(소모)칼로리
	
	private int startDate; // 칼로리 정보를 가져올 날짜의 시작일
	private int endDate; // 칼로리 정보를 가져올 날짜의 종료일
}

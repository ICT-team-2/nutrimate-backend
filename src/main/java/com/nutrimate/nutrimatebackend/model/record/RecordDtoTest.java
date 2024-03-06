package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("RecordDtoTest")
public class RecordDtoTest {
	private int userId; // 유저아이디
	
	private int recordId; // 기록아이디
	private int dietrecordId; // 식단기록아이디
	private int exerciseId; // 운동기록아이디
	
	private Integer foodId; // 음식아이디
	private String foodName; // 음식이름
	private Double foodCal; // 음식칼로리
	private Double foodCarbo;
	private Double foodProtein;
	private Double foodProvi;
	private Double userCal; // 일일권장칼로리
	
	private Integer sportId; // 운동아이디
	private String sportName; // 운동이름
	private Double sportMet; // Met
	private Date sportTime; // 운동시간
	private Double sportCal; // 운동(소모)칼로리
	
	private Date startDate; // 칼로리 정보를 가져올 날짜의 시작일
	private Date endDate; // 칼로리 정보를 가져올 날짜의 종료일
	
}

package com.nutrimate.nutrimatebackend.model.alarm;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("AlarmDto")
public class AlarmDto {
	
	private int alarmId;
	private int userId;
	private String alarmCategory;
	private String alarmTime;
	private String[] updatedAlarmWeek;
	private int createdDate;//전체 페이지
	private String startWeek ;//한주 시작
	private String endWeek ;//한주 끝

	
}

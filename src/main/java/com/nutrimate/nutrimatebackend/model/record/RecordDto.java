package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("RecordDto")
public class RecordDto {
	int userId;
	int recordId; // 기록 기본키
	String doDate; // 기록 날짜
	MealTime mealTime; // 식사 시간
}

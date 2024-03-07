package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Alias("FoodRecordDto")
public class FoodRecordDto extends FoodDto {
	
	
	RecordDto record;
	private Integer dietId;// 식단 기록 번호
	private MealTime mealTime; // 식사 시간
	private Integer recordIntake; // 섭취량
	private double serving; //몇인분인지?
	
	private List<Integer> foodIds;
	private List<Integer> recordIntakes;
}

package com.nutrimate.nutrimatebackend.model;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("FoodDto")
public class FoodDto {
	
	private Integer foodId; //음식 번호
	private String foodName; //음식 이름
	private String foodGroup; //음식 그룹
	
	private double foodCal; //음식 칼로리
	private double foodIntake; //음식 섭취량
	private String intakeUnit; //음식 섭취단위
	
	private double foodCarbo; //음식 탄수화물
	private double foodProtein; //음식 단백질
	private double foodProvi; //음식 지방
	
	private double foodChole; //음식 콜레스테롤
	private double foodSalt; //음식 나트륨
	
}

package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Alias("FoodDto")
public class FoodDto {
	private int userId; // 유저 기본키
	
	private Integer foodId; //음식 번호
	private String foodName; //음식 이름
	private String foodGroup; //음식 그룹
	
	private Double foodCal; //음식 칼로리
	private Integer foodIntake = 200; //음식 섭취량
	private String intakeUnit = "g"; //음식 섭취단위
	
	private Double foodCarbo; //음식 탄수화물
	private Double foodProtein; //음식 단백질
	private Double foodProvi; //음식 지방
	
	private Double foodChole; //음식 콜레스테롤
	private Double foodSalt; //음식 나트륨
	
}

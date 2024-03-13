package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.FoodRecordDto;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietRecordMapper {
	
	List<FoodRecordDto> findDietRecordByUserIdAndDoDate(RecordDto dto);
	
	//crud
	// 자신이 먹은 음식 기록하기 (식단DB 데이터 사용)
	int insertFoodRecord(FoodRecordDto dto);
	
	//기록시 음식이 식단DB에 없을 경우
	int insertCustomFood(FoodRecordDto dto);
}

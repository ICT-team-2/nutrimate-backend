package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordAnalysisMapper {
	// 먹은 칼로리와 일일 권장 칼로리 열람하기
	Integer findCaloriesEatenByUserId(@Param("userId") int userId);
	
	Integer findFoodRecommendedCaloriesByUserId(@Param("userId") int userId);
	
	Integer findTotalFoodCalories(RecordDto dtoTest);
}

package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.FoodDto;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DietSportRecordMapper {
	
	// 음식 목록 가져오기
	List<RecordDto> findFoodList(String searchWord);
	
	// 자신이 먹은 음식 기록하기 (식단DB 데이터 사용)
	void insertRecord(RecordDto recordDto);
	
	void insertFoodRecord(RecordDto recordDto);
	
	// 자신이 먹은 음식 기록하기 (유저가 직접 입력)
	void insertRecordFo(RecordDto recordDto);
	
	void insertRecordFood(RecordDto recordDto);
	
	void insertRecordCustomFood(RecordDto recordDto);
	
	void insertDietFoodRecord(RecordDto recordDto);
	
	// 먹은 칼로리와 일일 권장 칼로리 열람하기
	Integer findFoodCaloriesByUserId(RecordDto recordDto);
	
	Integer findFoodRecommendedCaloriesByUserId(RecordDto recordDto);
	
	// 운동 목록 가져오기
	List<RecordDto> findSportList(String searchWord);
	
	// 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
	void insertRecordExer(RecordDto recordDto);
	
	void insertExerciseRecord(RecordDto recordDto);
	
	// 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
	void insertRecordSp(RecordDto recordDto);
	
	void insertRecordSport(RecordDto recordDto);
	
	void insertRecordCustomSport(RecordDto recordDto);
	
	void insertCustomSportRecord(RecordDto recordDto);
	
	// 오늘 자신이 소모한 칼로리를 열람하는 쿼리문
	Integer findExerciseCaloriesByUserId(RecordDto recordDto);
	
	// 자신이 기록했던 운동의 소모한 칼로리 정보를 가져오기(식단 분석 그래프)
	Integer findTotalFoodCalories(RecordDto recordDto);
	
	// 자신이 기록했던 운동의 소모한 칼로리 정보를 가져오기(운동 분석 그래프)
	Integer findTotalSportCalories(RecordDto recordDto);
	
	List<RecordDto> findFoodListBySearchWord(String searchWord);
	
	List<FoodDto> findFoodListByFoodId(List<Integer> foodId);
}

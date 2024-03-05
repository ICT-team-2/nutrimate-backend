package com.nutrimate.nutrimatebackend.service.record;

import com.nutrimate.nutrimatebackend.mapper.record.DietSportRecordMapper;
import com.nutrimate.nutrimatebackend.model.FoodDto;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class DietSportRecordService {
	
	@Autowired
	private DietSportRecordMapper dietSportRecordMapper;
	
	// 음식 목록 가져오기
	public List<RecordDto> findFoodList(String searchWord) {
		return dietSportRecordMapper.findFoodListBySearchWord(searchWord);
	}
	
	public List<FoodDto> findFoodListByFoodId(List<Integer> foodId) {
		return dietSportRecordMapper.findFoodListByFoodId(foodId);
	}
	
	// 자신이 먹은 음식 기록하기 (식단DB 데이터 사용)
	@Transactional
	public void insertFoodRecord(RecordDto recordDto) {
		dietSportRecordMapper.insertRecord(recordDto);
		dietSportRecordMapper.insertFoodRecord(recordDto);
	}
	
	// 자신이 먹은 음식 기록하기 (유저가 직접 입력)
	@Transactional
	public void insertCustomFoodRecord(RecordDto recordDto) {
		dietSportRecordMapper.insertRecordFo(recordDto);
		log.info("foodId: " + recordDto.getFoodId());
		dietSportRecordMapper.insertRecordFood(recordDto);
		log.info("foodId222: " + recordDto.getFoodId());
		dietSportRecordMapper.insertRecordCustomFood(recordDto);
		dietSportRecordMapper.insertDietFoodRecord(recordDto);
	}
	
	// 먹은 칼로리와 일일 권장 칼로리 열람하기
	public int findFoodCaloriesByUserId(RecordDto recordDto) {
		Integer result = dietSportRecordMapper.findFoodCaloriesByUserId(recordDto);
		return (result != null) ? result : 0;
	}
	
	public int findFoodRecommendedCaloriesByUserId(RecordDto recordDto) {
		Integer result = dietSportRecordMapper.findFoodRecommendedCaloriesByUserId(recordDto);
		return (result != null) ? result : 0;
	}
	
	// 운동 목록 가져오기
	public List<RecordDto> findSportList(String searchWord) {
		return dietSportRecordMapper.findSportList(searchWord);
	}
	
	// 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
	@Transactional
	public void insertExerciseRecord(RecordDto recordDto) {
		dietSportRecordMapper.insertRecordExer(recordDto);
		dietSportRecordMapper.insertExerciseRecord(recordDto);
	}
	
	// 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
	@Transactional
	public void insertCustomSportRecord(RecordDto recordDto) {
		dietSportRecordMapper.insertRecordSp(recordDto);
		dietSportRecordMapper.insertRecordSport(recordDto);
		dietSportRecordMapper.insertRecordCustomSport(recordDto);
		dietSportRecordMapper.insertCustomSportRecord(recordDto);
	}
	
	// 오늘 자신이 운동으로 소모한 칼로리를 열람하는 쿼리문
	public int findExerciseCaloriesByUserId(RecordDto recordDto) {
		Integer result = dietSportRecordMapper.findExerciseCaloriesByUserId(recordDto);
		return (result != null) ? result : 0;
	}
	
	// 자신이 기록했던 운동의 소모한 칼로리 정보를 가져오기(식단 분석 그래프)
	public int findTotalFoodCalories(RecordDto recordDto) {
		Integer result = dietSportRecordMapper.findTotalFoodCalories(recordDto);
		return (result != null) ? result : 0;
	}
	
	// 자신이 기록했던 운동의 소모한 칼로리 정보를 가져오기(운동 분석 그래프)
	public int findTotalSportCalories(RecordDto recordDto) {
		Integer result = dietSportRecordMapper.findTotalSportCalories(recordDto);
		return (result != null) ? result : 0;
	}
	
}

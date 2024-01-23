package com.nutrimate.nutrimatebackend.service.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nutrimate.nutrimatebackend.mapper.test.DietSportRecordMapper;
import com.nutrimate.nutrimatebackend.model.member.DietSportRecordDto;

@Service
public class DietSportRecordService {

  @Autowired
  private DietSportRecordMapper dietSportRecordMapper;

  // 음식 목록 가져오기
  public List<DietSportRecordDto> findFoodList() {
    return dietSportRecordMapper.findFoodList();
  }

  // 자신이 먹은 음식 기록하기 (식단DB 데이터 사용)
  @Transactional
  public void insertRecord(DietSportRecordDto dietSportRecordDto) {
    dietSportRecordMapper.insertRecord(dietSportRecordDto);
  }

  @Transactional
  public void insertFoodRecord(DietSportRecordDto dietSportRecordDto) {
    dietSportRecordMapper.insertFoodRecord(dietSportRecordDto);
  }

  // 자신이 먹은 음식 기록하기 (유저가 직접 입력)
  public void insertCustomFoodRecord(DietSportRecordDto dietSportRecordDto) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userId", dietSportRecordDto.getUserId());
    paramMap.put("foodName", dietSportRecordDto.getFoodName());
    paramMap.put("calories", dietSportRecordDto.getFoodCal());
    dietSportRecordMapper.insertCustomFoodRecord(paramMap);
  }

  // 먹은 칼로리와 일일 권장 칼로리 열람하기
  public Integer findFoodCaloriesByUserId(int userId) {
    return dietSportRecordMapper.findFoodCaloriesByUserId(userId);
  }

  // 운동 목록 가져오기
  public List<DietSportRecordDto> findSportList() {
    return dietSportRecordMapper.findSportList();
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  public void insertExerciseRecord(DietSportRecordDto dietSportRecordDto) {
    dietSportRecordMapper.insertExerciseRecord(dietSportRecordDto);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  public void insertCustomExerciseRecord(DietSportRecordDto dietSportRecordDto) {
    Map<String, Object> paramMap = new HashMap<>();
    paramMap.put("userId", dietSportRecordDto.getUserId());
    paramMap.put("sportName", dietSportRecordDto.getSportName());
    paramMap.put("calories", dietSportRecordDto.getSportCal());
    paramMap.put("exerciseTime", dietSportRecordDto.getSportTime());
    dietSportRecordMapper.insertCustomExerciseRecord(paramMap);
  }

  // 오늘 자신이 소모한 칼로리를 열람하는 쿼리문
  public Integer findExerciseCaloriesByUserId(int userId) {
    return dietSportRecordMapper.findExerciseCaloriesByUserId(userId);
  }

}

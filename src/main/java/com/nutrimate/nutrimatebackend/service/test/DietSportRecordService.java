package com.nutrimate.nutrimatebackend.service.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.test.DietSportRecordMapper;
import com.nutrimate.nutrimatebackend.model.member.DietSportRecordDto;

@Service
public class DietSportRecordService {

  @Autowired
  private DietSportRecordMapper dietSportRecordMapper;

  // 음식 목록 가져오기
  public List<String> selectFoodList(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.selectFoodList();
  }

  // 자신이 먹은 음식을 기록하는 쿼리문 (식단DB 데이터 사용)
  public int insertDietRecord(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.insertDietRecord(0, null);
  }

  // 자신이 먹은 음식을 기록하는 쿼리문 (유저가 직접 입력)
  public int insertCustomFood(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.insertCustomFood(null, 0, 0);
  }

  // 자신이 먹은 칼로리를 일일 권장 칼로리와 함께 열람할 수 있는 쿼리문
  // 오늘 내가 먹은 칼로리
  public int selectCaloriesInfo(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.selectCaloriesInfo(0);
  }

  // 일일 권장 칼로리
  public int selectUserCalories(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.selectUserCalories(0);
  }

  // 운동 목록 가져오기
  public List<String> selectSportList(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.selectSportList();
  }

  // 운동 기록
  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  public int insertSportRecord(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.insertSportRecord(0, null, 0);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  public int insertCustomSport(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.insertCustomSport(null, 0, 0, 0);
  }

  public int selectTotalSportCalories(DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordMapper.selectTotalSportCalories(0);
  }

}

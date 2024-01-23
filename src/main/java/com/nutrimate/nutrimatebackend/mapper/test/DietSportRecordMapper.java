package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.member.DietSportRecordDto;

@Mapper
public interface DietSportRecordMapper {

  // 음식 목록 가져오기
  List<DietSportRecordDto> findFoodList();

  // 자신이 먹은 음식 기록하기 (식단DB 데이터 사용)
  void insertRecord(DietSportRecordDto dietSportRecordDto);

  void insertFoodRecord(DietSportRecordDto dietSportRecordDto);

  // 자신이 먹은 음식 기록하기 (유저가 직접 입력)
  void insertRecordFo(DietSportRecordDto dietSportRecordDto);

  void insertRecordFood(DietSportRecordDto dietSportRecordDto);

  void insertRecordCustomFood(DietSportRecordDto dietSportRecordDto);

  void insertCustomFoodRecord(DietSportRecordDto dietSportRecordDto);

  // 먹은 칼로리와 일일 권장 칼로리 열람하기
  Integer findFoodCaloriesByUserId(int userId);

  // 운동 목록 가져오기
  List<DietSportRecordDto> findSportList();

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  void insertRecordExer(DietSportRecordDto dietSportRecordDto);

  void insertExerciseRecord(DietSportRecordDto dietSportRecordDto);

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  void insertRecordSp(DietSportRecordDto dietSportRecordDto);

  void insertRecordSport(DietSportRecordDto dietSportRecordDto);

  void insertRecordCustomSport(DietSportRecordDto dietSportRecordDto);

  void insertCustomSportRecord(DietSportRecordDto dietSportRecordDto);

  // 오늘 자신이 소모한 칼로리를 열람하는 쿼리문
  Integer findExerciseCaloriesByUserId(int userId);

}

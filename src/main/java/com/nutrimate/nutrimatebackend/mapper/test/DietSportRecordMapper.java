package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DietSportRecordMapper {

  // userNick으로 userId를 가져오기
  // 입력 데이터 : userNick
  // @Select("SELECT user_id FROM member WHERE user_nick = #{userNick}")
  // int getUserIdByNick(String userNick);

  // 음식 목록 가져오기
  List<String> selectFoodList();

  // 자신이 먹은 음식을 기록하는 쿼리문 (식단DB 데이터 사용)
  // 입력 데이터 : userId, foodName
  int insertDietRecord(int userId, String foodName);

  // 자신이 먹은 음식을 기록하는 쿼리문 (유저가 직접 입력)
  // 입력 데이터 : userId, foodName, foodCal
  int insertCustomFood(String foodName, int foodCal, int userId);

  // 자신이 먹은 칼로리를 일일 권장 칼로리와 함께 열람할 수 있는 쿼리문
  // (운동을 하는 정도에 따라 권장 칼로리를 바꾼다-회원정보 기입시)
  // 오늘 내가 먹은 칼로리
  // 입력 데이터 : userId
  int selectCaloriesInfo(int userId);

  // 일일 권장 칼로리
  // 입력 데이터 : userId
  int selectUserCalories(int userId);


  // 운동 목록 가져오기
  // SELECT sporrt_name FROM sport
  List<String> selectSportList();

  // 운동 기록
  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  // 유저에게 받는 데이타는 '운동 이름', '운동 시간(분)
  // 운동 칼로리 계산식 : METs × 체중 (kg) × 1/60 × 운동 시간(분)
  int insertSportRecord(int userId, String sportName, int sportTime);

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  // 유저에게 받는 데이타는 '운동 이름', '운동 시간(분)', '칼로리'
  // METs 계산식 : METs = (칼로리 * 60) / (체중(kg) * 운동 시간(분))
  int insertCustomSport(String sportName, int sportCal, int sportTime, int userId);

  // 오늘 자신이 소모한 칼로리를 열람할 수 있는 쿼리문
  int selectTotalSportCalories(int userId);

}

package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DietSportRecordMapper {

  // userNick으로 userId를 가져오기
  // 입력 데이터 : userNick
  // @Select("SELECT user_id FROM member WHERE user_nick = #{userNick}")
  // int getUserIdByNick(String userNick);

  // 음식 목록 가져오기
  @Select("SELECT food_name FROM food "
  // + "WHERE food_name LIKE '김%' //검색 추가시 사용
  )
  List<String> selectFoodList();

  // 자신이 먹은 음식을 기록하는 쿼리문 (식단DB 데이터 사용)
  // 입력 데이터 : userId, foodName
  @Insert({
      "INSERT INTO record VALUES (SEQ_RECORD.NEXTVAL, (SELECT user_id FROM member WHERE user_id = #{userId}), SYSDATE, DEFAULT)",
      "INSERT INTO dietrecord VALUES (SEQ_DIETRECORD.NEXTVAL, (SELECT record_id FROM record WHERE user_id=#{userId}), (SELECT FOOD_ID FROM food WHERE FOOD_NAME=#{foodName}))"})
  int insertDietRecord(int userId, String foodName);

  // 자신이 먹은 음식을 기록하는 쿼리문 (유저가 직접 입력)
  // 입력 데이터 : userId, foodName, foodCal
  @Insert({"INSERT INTO food (food_id, food_name, food_cal) ",
      "VALUES ((SELECT COUNT(*)+1 FROM food), #{foodName}, #{foodCal})",
      "INSERT INTO record VALUES (SEQ_RECORD.NEXTVAL, (SELECT user_id FROM member WHERE user_id = #{userId}), SYSDATE, DEFAULT)",
      "INSERT INTO customfood VALUES ((SELECT FOOD_ID FROM FOOD WHERE FOOD_NAME=#{foodName}), #{userId}, SYSDATE)",
      "INSERT INTO dietrecord VALUES (SEQ_DIETRECORD.NEXTVAL, (SELECT SEQ_RECORD.CURRVAL FROM DUAL), (SELECT food_id FROM food WHERE food_name=#{foodName}))"})
  int insertCustomFood(String foodName, int foodCal, int userId);

  // 자신이 먹은 칼로리를 일일 권장 칼로리와 함께 열람할 수 있는 쿼리문
  // (운동을 하는 정도에 따라 권장 칼로리를 바꾼다-회원정보 기입시)
  // 오늘 내가 먹은 칼로리
  // 입력 데이터 : userId
  @Select("SELECT sum(food_cal) FROM food WHERE record_id=(SELECT record_id FROM record WHERE user_id=#{userId} AND do_date=SYSDATE)")
  int selectCaloriesInfo(int userId);

  // 일일 권장 칼로리
  // 입력 데이터 : userId
  @Select("SELECT user_cal FROM member WHERE user_id=#{userId}")
  int selectUserCalories(int userId);


  // 운동 목록 가져오기
  // SELECT sporrt_name FROM sport
  //
  @Select("SELECT sport_name FROM sport "
  // + "WHERE sporrt_name LIKE '바%'" //검색 추가시 사용
  )
  List<String> selectSportList();

  // 운동 기록
  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  // 유저에게 받는 데이타는 '운동 이름', '운동 시간(분)
  // 운동 칼로리 계산식 : METs × 체중 (kg) × 1/60 × 운동 시간(분)
  @Insert({
      "INSERT INTO record VALUES (SEQ_RECORD.NEXTVAL, (SELECT user_id FROM member WHERE user_id = #{userId}), SYSDATE, DEFAULT)",
      "INSERT INTO sportrecord VALUES (SEQ_SPORTRECORD.NEXTVAL, (SELECT record_id FROM record WHERE user_id=#{userId}), (SELECT sport_id FROM sport WHERE sport_name=#{sportName}), (SELECT sport_met FROM sport WHERE sport_name=#{sportName}), #{sportTime}, (SELECT user_weight FROM member WHERE user_id=#{userId}))"})
  int insertSportRecord(int userId, String sportName, int sportTime);

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  // 유저에게 받는 데이타는 '운동 이름', '운동 시간(분)', '칼로리'
  // METs 계산식 : METs = (칼로리 * 60) / (체중(kg) * 운동 시간(분))
  @Insert({
      "INSERT INTO record VALUES (SEQ_RECORD.NEXTVAL, (SELECT user_id FROM member WHERE user_id = #{userId}), SYSDATE, DEFAULT)",
      "INSERT INTO sport (sport_id, sport_name, sport_cal)",
      "VALUES ((SELECT COUNT(*)+1 FROM sport), #{sportName}, #{sportCal})",
      "INSERT INTO customsport VALUES ((SELECT sport_id FROM sport WHERE sport_name=#{sportName}), #{userId}, SYSDATE)",
      "INSERT INTO sportrecord VALUES (SEQ_SPORTRECORD.NEXTVAL, (SELECT SEQ_RECORD.CURRVAL FROM DUAL), (SELECT sport_id FROM sport WHERE sport_name=#{sportName}), #{sportCal}, #{sportTime}, (SELECT user_weight FROM member WHERE user_id=#{userId}))"})
  int insertCustomSport(String sportName, int sportCal, int sportTime, int userId);

  // 오늘 자신이 소모한 칼로리를 열람할 수 있는 쿼리문
  @Select("SELECT SUM(sport_cal) FROM sportrecord WHERE record_id=(SELECT record_id FROM record WHERE user_id=#{userId} AND do_date=SYSDATE)")
  int selectTotalSportCalories(int userId);

}

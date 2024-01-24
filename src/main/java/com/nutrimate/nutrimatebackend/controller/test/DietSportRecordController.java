package com.nutrimate.nutrimatebackend.controller.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.member.DietSportRecordDto;
import com.nutrimate.nutrimatebackend.service.test.DietSportRecordService;

@RestController
@RequestMapping("/api/record")
public class DietSportRecordController {

  @Autowired
  private DietSportRecordService dietSportRecordService;

  // 음식 목록 가져오기 (완료)
  // 입력 데이터 : 없음
  // 출력 데이터 : foodId, foodName, foodCal
  @GetMapping("/findFoodList")
  public ResponseEntity<List<Map<String, Object>>> findFoodList() {
    List<DietSportRecordDto> foodList = dietSportRecordService.findFoodList();
    List<Map<String, Object>> simplifiedFoodList = new ArrayList<>();
    for (DietSportRecordDto food : foodList) {
      Map<String, Object> simplifiedFood = new HashMap<>();
      simplifiedFood.put("foodId", food.getFoodId());
      simplifiedFood.put("foodName", food.getFoodName());
      simplifiedFood.put("foodCal", food.getFoodCal());
      simplifiedFoodList.add(simplifiedFood);
    }
    return new ResponseEntity<>(simplifiedFoodList, HttpStatus.OK);
  }

  // 자신이 먹은 음식 기록하기 (식단DB 데이터 사용) (완료)
  // 입력 데이터 : userId, foodName
  // 출력 데이터 : message, recordId, dietrecordId
  @PostMapping("/insertFoodRecord")
  public ResponseEntity<Map<String, Object>> insertFoodRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertFoodRecord(dietSportRecordDto);
    int recordId = dietSportRecordDto.getRecordId();
    int dietrecordId = dietSportRecordDto.getDietrecordId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Food Record successfully");
    jsonResponse.put("recordId", recordId);
    jsonResponse.put("dietrecordId", dietrecordId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 자신이 먹은 음식 기록하기 (유저가 직접 입력) (완료)
  // 입력 데이터 : userId, foodName, foodCal
  // 출력 데이터 : message, recordId, dietrecordId
  @PostMapping("/insertCustomFoodRecord")
  public ResponseEntity<Map<String, Object>> insertCustomFoodRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertCustomFoodRecord(dietSportRecordDto);
    int recordId = dietSportRecordDto.getRecordId();
    int dietrecordId = dietSportRecordDto.getDietrecordId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Custom Food Record successfully");
    jsonResponse.put("recordId", recordId);
    jsonResponse.put("dietrecordId", dietrecordId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 먹은 칼로리와 일일 권장 칼로리 열람하기 (완료)
  // 하루동안 먹은 칼로리나 일일 권장칼로리의 값이 없을시 값을 0처리
  // 입력 데이터 : userId
  // 출력 데이터 : foodCal(먹은 칼로리), userCal(일일 권장 칼로리)
  @GetMapping("/findFoodCaloriesByUserId")
  public ResponseEntity<Map<String, Object>> findFoodCaloriesByUserId(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    int foodCal = dietSportRecordService.findFoodCaloriesByUserId(dietSportRecordDto);
    int userCal = dietSportRecordService.findFoodRecommendedCaloriesByUserId(dietSportRecordDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("foodCal", foodCal);
    jsonResponse.put("userCal", userCal);
    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
  }

  // 운동 목록 가져오기 (완료)
  // 입력 데이터 : 없음
  // 출력 데이터 : sportId, sportName, sportMet
  @GetMapping("/findSportList")
  public ResponseEntity<List<Map<String, Object>>> findSportList() {
    List<DietSportRecordDto> sportList = dietSportRecordService.findSportList();
    List<Map<String, Object>> simplifiedsportList = new ArrayList<>();
    for (DietSportRecordDto sport : sportList) {
      Map<String, Object> simplifiedSport = new HashMap<>();
      simplifiedSport.put("sportId", sport.getSportId());
      simplifiedSport.put("sportName", sport.getSportName());
      simplifiedSport.put("sportMet", sport.getSportMet());
      simplifiedsportList.add(simplifiedSport);
    }
    return new ResponseEntity<>(simplifiedsportList, HttpStatus.OK);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용) (완료)
  // 입력 데이터 : userId, sportName, sportTime
  // 출력 데이터 : message, recordId, exerciseId
  @PostMapping("/insertExerciseRecord")
  public ResponseEntity<Map<String, Object>> insertExerciseRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertExerciseRecord(dietSportRecordDto);
    int recordId = dietSportRecordDto.getRecordId();
    int exerciseId = dietSportRecordDto.getExerciseId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Exercise Record successfully");
    jsonResponse.put("recordId", recordId);
    jsonResponse.put("exerciseId", exerciseId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력) (완료)
  // 입력 데이터 : userId, sportName, sportCal, sportTime
  // 출력 데이터 : message, recordId, exerciseId
  @PostMapping("/insertCustomSportRecord")
  public ResponseEntity<Map<String, Object>> insertCustomSportRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertCustomSportRecord(dietSportRecordDto);
    int recordId = dietSportRecordDto.getRecordId();
    int exerciseId = dietSportRecordDto.getExerciseId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Exercise Record successfully");
    jsonResponse.put("recordId", recordId);
    jsonResponse.put("exerciseId", exerciseId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 오늘 자신이 소모한 칼로리를 열람하는 쿼리문 (완료)
  // 하루동안 소모한 칼로리 값이 없을시 값을 0처리
  // 입력 데이터 : userId
  // 출력 데이터 : sportCal(소모한 칼로리)
  @GetMapping("/findExerciseCaloriesByUserId")
  public ResponseEntity<Map<String, Object>> findExerciseCaloriesByUserId(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    int sportCal = dietSportRecordService.findExerciseCaloriesByUserId(dietSportRecordDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("sportCal", sportCal);
    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
  }

  // 자신이 기록했던 운동의 소모한 칼로리 정보를 가져오기(식단,운동 분석 그래프)
  // totalFoodCalories나 totalSportCalories 값이 없을시 0처리
  // 입력 데이터 : userId, startDate, endDate (입력데이터 예시 : 20240101)
  // 출력 데이터 : totalFoodCalories, totalSportCalories
  @GetMapping("/findTotalCalories")
  public ResponseEntity<Map<String, Object>> findTotalCalories(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    // try {
    int totalFoodCalories = dietSportRecordService.findTotalFoodCalories(dietSportRecordDto);
    int totalSportCalories = dietSportRecordService.findTotalSportCalories(dietSportRecordDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("totalFoodCalories", totalFoodCalories);
    jsonResponse.put("totalSportCalories", totalSportCalories);
    return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    // } catch (Exception e) { // 유저를 못찾았다면 error 반환
    // Map<String, Object> errorResponse = new HashMap<>();
    // errorResponse.put("error", "User not found");
    // return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    // }
  }

}

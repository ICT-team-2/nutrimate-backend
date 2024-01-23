package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  @GetMapping("/FoodList")
  public ResponseEntity<List<DietSportRecordDto>> findFoodList() {
    List<DietSportRecordDto> foodList = dietSportRecordService.findFoodList();
    return new ResponseEntity<>(foodList, HttpStatus.OK);
  }

  // 자신이 먹은 음식 기록하기 (식단DB 데이터 사용) (완료)
  // 입력 데이터 : userId, foodName
  @PostMapping("/logEatenFood")
  public ResponseEntity<String> insertFoodRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertRecord(dietSportRecordDto);
    dietSportRecordService.insertFoodRecord(dietSportRecordDto);
    // JSON 형식으로 응답
    String jsonResponse = "{\"message\": \"Food Record successfully\"}";
    return ResponseEntity.ok(jsonResponse);
  }

  // 자신이 먹은 음식 기록하기 (유저가 직접 입력)
  // 입력 데이터 : userId, foodName, calories
  @PostMapping("/logCustomFood")
  public ResponseEntity<String> logCustomFood(@RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertCustomFoodRecord(dietSportRecordDto);
    return new ResponseEntity<>("Custom food Record successfully", HttpStatus.OK);
  }

  // 먹은 칼로리와 일일 권장 칼로리 열람하기
  // 입력 데이터 : userId
  @GetMapping("/viewCaloriesConsumed")
  public ResponseEntity<Integer> viewCaloriesConsumed(@RequestParam int userId) {
    int consumedCalories = dietSportRecordService.findFoodCaloriesByUserId(userId);
    return new ResponseEntity<>(consumedCalories, HttpStatus.OK);
  }

  // 운동 목록 가져오기 (완료)
  // 입력 데이터 : 없음
  // 출력 데이터 : sportId, sportName, sportMet
  @GetMapping("/SportList")
  public ResponseEntity<List<DietSportRecordDto>> findSportList() {
    List<DietSportRecordDto> sportList = dietSportRecordService.findSportList();
    return new ResponseEntity<>(sportList, HttpStatus.OK);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용) (진행중)
  // 입력 데이터 : userId, sportName, sportTime
  @PostMapping("/logExerciseWithDBData")
  public ResponseEntity<String> insertExerciseRecord(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    // dietSportRecordService.insertRecord(dietSportRecordDto);
    dietSportRecordService.insertExerciseRecord(dietSportRecordDto);
    return new ResponseEntity<>("Exercise Record successfully", HttpStatus.OK);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  // 입력 데이터 : userId, sportName, calories, exerciseTime
  @PostMapping("/logCustomExercise")
  public ResponseEntity<String> logCustomExercise(
      @RequestBody DietSportRecordDto dietSportRecordDto) {
    dietSportRecordService.insertCustomExerciseRecord(dietSportRecordDto);
    return new ResponseEntity<>("Custom exercise Record successfully", HttpStatus.OK);
  }

  // 오늘 자신이 소모한 칼로리를 열람하는 쿼리문
  // 입력 데이터 : userId
  @GetMapping("/viewExerciseCalories")
  public ResponseEntity<Integer> viewExerciseCalories(@RequestParam int userId) {
    int consumedCalories = dietSportRecordService.findExerciseCaloriesByUserId(userId);
    return new ResponseEntity<>(consumedCalories, HttpStatus.OK);
  }

}

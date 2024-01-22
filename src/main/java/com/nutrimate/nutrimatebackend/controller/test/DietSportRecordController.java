package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

  /**
   * POST - CREATE GET - SELECT PUT - UPDATE DELETE - DELETE
   */

  @Autowired
  private DietSportRecordService dietSportRecordService;

  // 음식 목록 가져오기
  @GetMapping("/FoodList")
  public List<String> selectFoodList(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.selectFoodList(dietSportRecordDto);
  }

  // 자신이 먹은 음식을 기록하는 쿼리문 (식단DB 데이터 사용)
  @PostMapping("/Recommend")
  public int insertDietRecord(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.insertDietRecord(dietSportRecordDto);
  }

  // 자신이 먹은 음식을 기록하는 쿼리문 (유저가 직접 입력)
  @PostMapping("/Recommend")
  public int insertCustomFood(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.insertCustomFood(dietSportRecordDto);
  }

  // 자신이 먹은 칼로리를 일일 권장 칼로리와 함께 열람할 수 있는 쿼리문
  // 오늘 내가 먹은 칼로리
  @GetMapping("/Recommend")
  public int selectCaloriesInfo(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.selectCaloriesInfo(dietSportRecordDto);
  }

  // 일일 권장 칼로리
  @GetMapping("/Recommend")
  public int selectUserCalories(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.selectUserCalories(dietSportRecordDto);
  }

  // 운동 목록 가져오기
  @GetMapping("/Recommend")
  public List<String> selectSportList(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.selectSportList(dietSportRecordDto);
  }

  // 운동 기록
  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (운동DB 데이터 사용)
  @PostMapping("/Recommend")
  public int insertSportRecord(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.insertSportRecord(dietSportRecordDto);
  }

  // 운동으로 소모한 칼로리를 기록하는 쿼리문 (유저가 직접 입력)
  @PostMapping("/Recommend")
  public int insertCustomSport(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.insertCustomSport(dietSportRecordDto);
  }

  // 오늘 자신이 소모한 칼로리를 열람할 수 있는 쿼리문
  @GetMapping("/Recommend")
  public int selectTotalSportCalories(@RequestBody DietSportRecordDto dietSportRecordDto) {
    return dietSportRecordService.selectTotalSportCalories(dietSportRecordDto);
  }

}

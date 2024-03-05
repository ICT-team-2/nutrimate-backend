package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.service.record.DietSportRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
@Log4j2
public class RecordController {
	
	@Autowired
	private DietSportRecordService dietSportRecordService;
	
	// 음식 목록 가져오기 (완료)
	// 입력 데이터 : 없음, [searchWord]
	// 출력 데이터 : foodId, foodName, foodCal
	@GetMapping("/foodlist")
	public ResponseEntity<List<Map<String, Object>>> findFoodList(
			@RequestParam(value = "searchWord", required = false) String searchWord) {
		List<RecordDto> foodList = dietSportRecordService.findFoodList(searchWord);
		List<Map<String, Object>> simplifiedFoodList = new ArrayList<>();
		for (RecordDto food : foodList) {
			Map<String, Object> simplifiedFood = new HashMap<>();
			simplifiedFood.put("foodId", food.getFoodId());
			simplifiedFood.put("foodName", food.getFoodName());
			simplifiedFood.put("foodCal", food.getFoodCal());
			simplifiedFood.put("Protein", food.getFoodProtein());
			simplifiedFood.put("Province", food.getFoodProvi());
			simplifiedFood.put("Carbo", food.getFoodCarbo());
			simplifiedFoodList.add(simplifiedFood);
		}
		return new ResponseEntity<>(simplifiedFoodList, HttpStatus.OK);
	}
	
	// 자신이 먹은 음식 기록하기 (식단DB 데이터 사용) (완료)
	// 입력 데이터 : userId, foodName
	// 출력 데이터 : message, recordId, dietrecordId
	@PostMapping("/food")
	public ResponseEntity<Map<String, Object>> insertFoodRecord(
			@RequestBody RecordDto recordDto) {
		try {
			dietSportRecordService.insertFoodRecord(recordDto);
			int recordId = recordDto.getRecordId();
			int dietrecordId = recordDto.getDietrecordId();
			Map<String, Object> jsonResponse = new HashMap<>();
			jsonResponse.put("message", "Food Record successfully");
			jsonResponse.put("recordId", recordId);
			jsonResponse.put("dietrecordId", dietrecordId);
			return ResponseEntity.ok(jsonResponse);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			Map<String, Object> errors = new HashMap<>();
			errors.put("message", "목록에 존재하지 않는 foodName입니다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
	}
	
	// 자신이 먹은 음식 기록하기 (유저가 직접 입력) (완료)
	// 입력 데이터 : userId, foodName, foodCal
	// 출력 데이터 : message, recordId, dietrecordId
	@PostMapping("/food/custom")
	public ResponseEntity<Map<String, Object>> insertCustomFoodRecord(
			@RequestBody RecordDto recordDto) {
		try {
			dietSportRecordService.insertCustomFoodRecord(recordDto);
			int recordId = recordDto.getRecordId();
			int dietrecordId = recordDto.getDietrecordId();
			Map<String, Object> jsonResponse = new HashMap<>();
			jsonResponse.put("message", "Custom Food Record successfully");
			jsonResponse.put("recordId", recordId);
			jsonResponse.put("dietrecordId", dietrecordId);
			return ResponseEntity.ok(jsonResponse);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			Map<String, Object> errors = new HashMap<>();
			errors.put("message", "이미 목록에 있는 foodName입니다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
	}
	
	@GetMapping("/food/calories")
	public ResponseEntity<Map<String, Object>> findFoodCaloriesByUserIdGet(
			@RequestParam(value = "userId", required = false) int userId) {
		RecordDto recordDto = new RecordDto();
		recordDto.setUserId(userId);
		int foodCal = dietSportRecordService.findFoodCaloriesByUserId(recordDto);
		int userCal = dietSportRecordService.findFoodRecommendedCaloriesByUserId(recordDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("foodCal", foodCal);
		jsonResponse.put("userCal", userCal);
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	// 먹은 칼로리와 일일 권장 칼로리 열람하기 (완료)
	// 하루동안 먹은 칼로리나 일일 권장칼로리의 값이 없을시 값을 0처리
	// 입력 데이터 : userId
	// 출력 데이터 : foodCal(먹은 칼로리), userCal(일일 권장 칼로리)
	@PostMapping("/food/calories")
	public ResponseEntity<Map<String, Object>> findFoodCaloriesByUserId(
			@RequestBody RecordDto recordDto) {
		int foodCal = dietSportRecordService.findFoodCaloriesByUserId(recordDto);
		int userCal = dietSportRecordService.findFoodRecommendedCaloriesByUserId(recordDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("foodCal", foodCal);
		jsonResponse.put("userCal", userCal);
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	// 운동 목록 가져오기 (완료)
	// 입력 데이터 : 없음, [searchWord]
	// 출력 데이터 : sportId, sportName, sportMet
	@GetMapping("/sportlist")
	public ResponseEntity<List<Map<String, Object>>> findSportList(
			@RequestParam(required = false) String searchWord) {
		List<RecordDto> sportList = dietSportRecordService.findSportList(searchWord);
		List<Map<String, Object>> simplifiedsportList = new ArrayList<>();
		for (RecordDto sport : sportList) {
			Map<String, Object> simplifiedSport = new HashMap<>();
			simplifiedSport.put("sportId", sport.getSportId());
			simplifiedSport.put("sportName", sport.getSportName());
			simplifiedSport.put("sportMet", sport.getSportMet());
			simplifiedsportList.add(simplifiedSport);
		}
		return new ResponseEntity<>(simplifiedsportList, HttpStatus.OK);
	}
	
	// 운동으로 소모한 칼로리를 기록하기 (관리자가 입력한 운동DB 데이터 사용) (완료)
	// 입력 데이터 : userId, sportName, sportTime
	// 출력 데이터 : message, recordId, exerciseId
	@PostMapping("/sport")
	public ResponseEntity<Map<String, Object>> insertExerciseRecord(
			@RequestBody RecordDto recordDto) {
		try {
			dietSportRecordService.insertExerciseRecord(recordDto);
			int recordId = recordDto.getRecordId();
			int exerciseId = recordDto.getExerciseId();
			Map<String, Object> jsonResponse = new HashMap<>();
			jsonResponse.put("message", "Exercise Record successfully");
			jsonResponse.put("recordId", recordId);
			jsonResponse.put("exerciseId", exerciseId);
			return ResponseEntity.ok(jsonResponse);
		} catch (Exception e) {
			Map<String, Object> errors = new HashMap<>();
			errors.put("message", "목록에 존재하지 않는 sportName입니다");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		}
	}
	
	// 운동으로 소모한 칼로리를 기록하기 (유저가 직접 입력) (완료)
	// 입력 데이터 : userId, sportName, sportCal, sportTime
	// 출력 데이터 : message, recordId, exerciseId
	@PostMapping("/sport/custom")
	public ResponseEntity<Map<String, Object>> insertCustomSportRecord(
			@RequestBody RecordDto recordDto) {
		// try {
		dietSportRecordService.insertCustomSportRecord(recordDto);
		int recordId = recordDto.getRecordId();
		int exerciseId = recordDto.getExerciseId();
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("message", "Exercise Record successfully");
		jsonResponse.put("recordId", recordId);
		jsonResponse.put("exerciseId", exerciseId);
		return ResponseEntity.ok(jsonResponse);
		// } catch (Exception e) {
		// Map<String, Object> errors = new HashMap<>();
		// errors.put("message", "이미 목록에 있는 sportName입니다");
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		// }
	}
	
	
	// 오늘 자신이 소모한 칼로리를 열람하기 (완료)
	// 하루동안 소모한 칼로리 값이 없을시 값을 0처리
	// 입력 데이터 : userId
	// 출력 데이터 : sportCal(소모한 칼로리)
	@GetMapping("/sport/calories")
	public ResponseEntity<Map<String, Object>> findExerciseCaloriesByUserId(
			@RequestBody RecordDto recordDto) {
		int sportCal = dietSportRecordService.findExerciseCaloriesByUserId(recordDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("sportCal", sportCal);
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
	// 자신이 기록했던 칼로리 정보를 가져오기(식단,운동 분석 그래프) (완료)
	// totalFoodCalories나 totalSportCalories 값이 없을시 0처리
	// 입력 데이터 : userId, startDate, endDate (입력데이터 예시 : 20240101)
	// 출력 데이터 : totalFoodCalories, totalSportCalories
	@GetMapping("/total/calories")
	public ResponseEntity<Map<String, Object>> findTotalCalories(
			@RequestBody RecordDto recordDto) {
		int totalFoodCalories = dietSportRecordService.findTotalFoodCalories(recordDto);
		int totalSportCalories = dietSportRecordService.findTotalSportCalories(recordDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("totalFoodCalories", totalFoodCalories);
		jsonResponse.put("totalSportCalories", totalSportCalories);
		return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
	}
	
}

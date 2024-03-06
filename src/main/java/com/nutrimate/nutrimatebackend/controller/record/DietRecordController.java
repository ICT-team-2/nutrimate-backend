package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.FoodRecordDto;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.service.record.DietRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record/diet")
public class DietRecordController {
	
	private DietRecordService dietRecordService;
	
	public DietRecordController(DietRecordService dietRecordService) {
		this.dietRecordService = dietRecordService;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> handleRuntimeException(RuntimeException e) {
		return Map.of("message", e.getMessage(),
				"succeed", false);
	}
	
	//식단 기록 가져오기
	/**
	 * @param dto {@link FoodRecordDto}
	 *            - userId : 사용자 아이디
	 *            - doDate : 기록 날짜
	 *
	 * @return List<FoodRecordDto>
	 */
	@GetMapping()
	public List<FoodRecordDto> findDietRecordByUserIdAndDoDate(RecordDto dto) {
		return dietRecordService.findDietRecordByUserIdAndDoDate(dto);
	}
	
	//식단 기록하기(DB)
	@PostMapping("/db")
	public Map<String, Object> insertFoodRecordWithDB(@RequestBody FoodRecordDto dto) {
		int affected = dietRecordService.insertFoodRecordWithDB(dto);
		
		if (affected == 0) {
			throw new RuntimeException("식단 기록에 실패했습니다.");
		}
		return Map.of("message", "식단 기록에 성공했습니다.",
				"succeed", true);
	}
	
	//식단 기록하기(custom)
	@PostMapping("/custom")
	public Map<String, Object> insertFoodRecordWithCustom(@RequestBody FoodRecordDto dto) {
		int affected = dietRecordService.insertFoodRecordWithCustom(dto);
		
		if (affected == 0) {
			throw new RuntimeException("식단 기록에 실패했습니다.");
		}
		return Map.of("message", "식단 기록에 성공했습니다.",
				"succeed", true);
	}
	
	@DeleteMapping()
	public Map<String, Object> deleteRecord(RecordDto dto) {
		if (dietRecordService.isAlreadyDeletedRecord(dto.getRecordId())) {
			throw new RuntimeException("이미 삭제된 기록입니다.");
		}
		
		int affected = dietRecordService.deleteRecord(dto);
		
		if (affected == 0) {
			throw new RuntimeException("기록 삭제에 실패했습니다.");
		}
		return Map.of("message", "기록 삭제에 성공했습니다.",
				"succeed", true);
	}
}

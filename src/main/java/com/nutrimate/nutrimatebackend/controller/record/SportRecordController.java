package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.model.record.SportRecordDto;
import com.nutrimate.nutrimatebackend.service.record.SportRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record/sport")
public class SportRecordController {
	
	private SportRecordService service;
	
	public SportRecordController(SportRecordService service) {
		this.service = service;
	}
	
	//운동 기록 가져오기
	@GetMapping()
	public List<SportRecordDto> findSportRecordByUserIdAndDoDate(RecordDto dto) {
		return service.findSportRecordByUserIdAndDoDate(dto);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> handleRuntimeException(RuntimeException e) {
		return Map.of("message", e.getMessage(),
				"succeed", false);
	}
	
	//운동 기록하기(DB)
	@PostMapping("/db")
	public Map<String, Object> insertSportRecordWithDB(@RequestBody SportRecordDto dto) {
		int affected = service.insertSportRecordWithDB(dto);
		
		if (affected == 0) {
			throw new RuntimeException("운동 기록에 실패했습니다.");
		}
		return Map.of("message", "운동 기록에 성공했습니다.",
				"succeed", true);
	}
	
	
	//운동 기록 삭제하기
	@DeleteMapping()
	public Map<String, Object> deleteRecord(RecordDto dto) {
		if (service.isAlreadyDeletedRecord(dto.getRecordId())) {
			throw new RuntimeException("이미 삭제된 기록입니다.");
		}
		
		int affected = service.deleteRecord(dto);
		if (affected == 0) {
			throw new RuntimeException("기록 삭제에 실패했습니다.");
		}
		
		return Map.of("message", "기록 삭제에 성공했습니다.",
				"succeed", true);
	}
}

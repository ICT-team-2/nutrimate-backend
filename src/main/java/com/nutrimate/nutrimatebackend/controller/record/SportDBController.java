package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import com.nutrimate.nutrimatebackend.model.record.SportDto;
import com.nutrimate.nutrimatebackend.service.record.SportRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SportDBController {
	
	private SportRecordService service;
	
	public SportDBController(SportRecordService service) {
		this.service = service;
	}
	
	//운동 DB로부터 운동정보 가져오기
	//운동명으로 운동정보 가져오기
	@GetMapping("/sport")
	public List<SportDto> findSportListBySearchWord(RecordPagingDto dto) {
		return service.findSportListBySearchWord(dto);
	}
	
}

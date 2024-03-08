package com.nutrimate.nutrimatebackend.controller.record;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import com.nutrimate.nutrimatebackend.service.record.SportRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SportDBController {
	
	private SportRecordService service;
	private ObjectMapper objectMapper;
	
	public SportDBController(SportRecordService service, ObjectMapper objectMapper) {
		this.service = service;
		this.objectMapper = objectMapper;
	}
	
	//운동 DB로부터 운동정보 가져오기
	//운동명으로 운동정보 가져오기
	@GetMapping("/sport")
	public Map<String, Object> findSportListBySearchWord(RecordPagingDto dto) {
		int totalCount = service.findCountSportList(dto);
		dto.setTotalCount(totalCount);
		dto.setTotalPage((int) Math.ceil((double) totalCount / dto.getReceivePage()));
		
		Map<String, Object> map = objectMapper.convertValue(dto, Map.class);
		map.put("sportList", service.findSportListBySearchWord(dto));
		return map;
	}
	
}

package com.nutrimate.nutrimatebackend.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.PagingDto;
import com.nutrimate.nutrimatebackend.service.board.InfoBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class InfoBoardController {
	
	private InfoBoardService infoBoardService;
	private ObjectMapper objectMapper;
	
	public InfoBoardController(InfoBoardService infoBoardService, ObjectMapper objectMapper) {
		this.infoBoardService = infoBoardService;
		this.objectMapper = objectMapper;
	}
	
	@GetMapping("/board/info/list")
	public Map<String, Object> findAllInfoBoardList(PagingDto dto) {
		int count = infoBoardService.countAllInfoBoardList(dto);
		log.info("findAllInfoBoardList count: " + count);
		dto.setTotalPage((int) Math.ceil((double) count / dto.getReceivePage()));
		
		Map<String, Object> result = objectMapper.convertValue(dto, HashMap.class);
		List<InfoBoardDto> list = infoBoardService.findAllInfoBoardList(dto);
		result.put("boardList", list);
		return result;
	}
	
	@PutMapping("/board/view/count")
	public Map<String, String> updateViewCount(@RequestBody Map<String, Object> map) {
		int count = infoBoardService.updateViewCount((int) map.get("boardId"));
		Map<String, String> result = new HashMap<>();
		result.put("result", count == 1 ? "success" : "fail");
		result.put("message", count == 1 ? "조회수 증가 성공" : "조회수 증가 실패");
		return result;
	}
	
}

package com.nutrimate.nutrimatebackend.controller.report;

import com.nutrimate.nutrimatebackend.model.report.ReportDto;
import com.nutrimate.nutrimatebackend.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
public class ReportController {
	
	@Autowired
	private ReportService blockService;
	
	//한 게시글 or 댓글 당 한사람만 신고하도록 하기
	@PostMapping("report")
	public Map WriteBlock(@RequestBody ReportDto dto) {
		Map map = new HashMap();
		String searchKeyWord = dto.getSeachKeyWord();
		if (searchKeyWord.equals("BOARD")) {
			int count = blockService.countboarder(dto);//
			if (count == 0) {
				int affected = blockService.saveBoardReport(dto);
				if (affected == 1) {
					map.put("REPORTOK", "게시물 신고 완료했습니다.");
				} else {
					map.put("REPORTOK", "게시물 신고에 실패했습니다!");
					
				}
				
			} else {
				map.put("REPORTOK", "이미 신고한 게시물입니다.");
			}
			return map;
		} else {
			int count = blockService.countcomment(dto);
			if (count == 0) {
				int affected = blockService.saveCommentReport(dto);
				if (affected == 1) {
					map.put("REPORTOK", "댓글 신고 완료했습니다.");
				} else {
					map.put("REPORTOK", "댓글 신고에 실패했습니다!");
					
				}
				
			} else {
				map.put("REPORTOK", "이미 신고한 댓글입니다.");
			}
			return map;
			
		}
	}
	
	
}
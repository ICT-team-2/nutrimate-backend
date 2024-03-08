package com.nutrimate.nutrimatebackend.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.admin.StatisticsDto;
import com.nutrimate.nutrimatebackend.service.admin.StatisticsService;

@RestController
@RequestMapping("/statistic")
public class StatisticsController {
	@Autowired
	private StatisticsService statisticsService;
	
	
	@GetMapping("/list/best")//인기글
	public List<StatisticsDto> bestList() {
		List<StatisticsDto> bestList = statisticsService.selectBestList();
		return bestList;
		
	}
	
	
	@GetMapping("/list/member/week")//주간 가입자수
	public List<StatisticsDto> weekMemberList() {
		List<StatisticsDto> weekList = statisticsService.selectMemeberWeek();
		return weekList;
		
	}
	@GetMapping("/list/member/month")//월간 가입자수
	public List<StatisticsDto> monthMemberList() {
		List<StatisticsDto> weekList = statisticsService.selectMemeberMonth();
		return weekList;
		
	}
	@GetMapping("/list/category")//주간 카테고리별
	public List<StatisticsDto> categoryList() {
		List<StatisticsDto> categoryList = statisticsService.selectCategoryList();
		return categoryList;
		
	}
	
	@GetMapping("/list/category/month")//월 카테고리별 게시글 수
	public List<StatisticsDto> categoryBoardList() {
		List<StatisticsDto> categoryBoardList = statisticsService.selectCategoryBoardList();
		return categoryBoardList;
		
	}
	
	
}

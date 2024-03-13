package com.nutrimate.nutrimatebackend.service.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.admin.StatisticsMapper;
import com.nutrimate.nutrimatebackend.model.admin.StatisticsDto;

@Service
public class StatisticsService {
  
    @Autowired
    private StatisticsMapper statisticsmapper;
  //주간 카테고리별   
    public List<StatisticsDto> selectMemeberWeek() {
      
      return statisticsmapper.findWeekMember();
  }
  //월간 가입자수     
    public List<StatisticsDto> selectMemeberMonth() {
      return statisticsmapper.findMonthMember();
    }
  //주간 카테고리별
    public List<StatisticsDto> selectCategoryList() {
      return statisticsmapper.findByCategory();
    }
  //인기글   
    public List<StatisticsDto> selectBestList() {
      return statisticsmapper.findBestBylikeAndBoardviewcount();
    }
  //월 카테고리별 게시글 수
	public List<StatisticsDto> selectCategoryBoardList() {
		 return statisticsmapper.findBestByBoardCategory();
	}



    

    
    
    
    

}

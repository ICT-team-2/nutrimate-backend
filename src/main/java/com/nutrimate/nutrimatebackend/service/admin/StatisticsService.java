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
    
    public List<StatisticsDto> selectMemeberWeek() {
      
      return statisticsmapper.findWeekMember();
  }
    
    public List<StatisticsDto> selectMemeberMonth() {
      return statisticsmapper.findMonthMember();
    }

    public List<StatisticsDto> selectCategoryList() {
      return statisticsmapper.findByCategory();
    }

    public List<StatisticsDto> selectBestList() {
      return statisticsmapper.findBestBylikeAndBoardviewcount();
    }



    

    
    
    
    

}

package com.nutrimate.nutrimatebackend.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.admin.StatisticsDto;
import com.nutrimate.nutrimatebackend.service.admin.StatisticsService;

@RestController
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    
    
    @GetMapping("/BestList.do")//인기글
    public List<StatisticsDto> BestList() {
        List<StatisticsDto> bestList =statisticsService.selectBestList();
          return bestList;
        
    }
    
    
    @GetMapping("/WeekMemberList.do")//주간 가입자수 
    public List<StatisticsDto> WeekMemberList() {
        List<StatisticsDto> weekList =statisticsService.selectMemeberWeek();
          return weekList;
        
    }
    @GetMapping("/MonthMemberList.do")//월간 가입자수 
    public List<StatisticsDto> MonthMemberList() {
        List<StatisticsDto> weekList =statisticsService.selectMemeberMonth();
          return weekList;
        
    }
    @GetMapping("/CategoryList.do")//주간 카테고리별
    public List<StatisticsDto> CategoryList() {
      List<StatisticsDto> categoryList =statisticsService.selectCategoryList();
        return categoryList;
      
   }
    
    
    
    
}

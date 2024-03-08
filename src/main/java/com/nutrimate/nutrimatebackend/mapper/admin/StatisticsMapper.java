package com.nutrimate.nutrimatebackend.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.admin.StatisticsDto;

@Mapper
public interface StatisticsMapper {

      List<StatisticsDto> findWeekMember();//주간 가입자수 

      List<StatisticsDto> findByCategory();//주간 카테고리별

      List<StatisticsDto> findMonthMember();//월간 가입자수 

      List<StatisticsDto> findBestBylikeAndBoardviewcount();//인기글
     
    //월 카테고리별 게시글 수
	  List<StatisticsDto> findBestByBoardCategory();

}

package com.nutrimate.nutrimatebackend.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.admin.StatisticsDto;

@Mapper
public interface StatisticsMapper {

      List<StatisticsDto> findWeekMember();

      List<StatisticsDto> findByCategory();

      List<StatisticsDto> findMonthMember();

      List<StatisticsDto> findBestBylikeAndBoardviewcount();

}

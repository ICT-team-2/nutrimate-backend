package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import com.nutrimate.nutrimatebackend.model.record.SportDto;
import com.nutrimate.nutrimatebackend.model.record.SportRecordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportDBMapper {
	//crud
	//read
	List<SportDto> findSportListBySearchWord(RecordPagingDto dto);
	
	int findCountSportList(RecordPagingDto dto);
	
	//create
	int insertSport(SportRecordDto sportDto);
	
	//update (사용x)
//	int updateSport(SportRecordDto sportDto);
	
	//delete (사용x)
//	int deleteSport(@Param("sportId") int sportId);
	
	double findMetBySportId(int sportId);
}

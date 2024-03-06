package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.model.record.SportRecordDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SportRecordMapper {
	List<SportRecordDto> findSportRecordByUserIdAndDoDate(RecordDto dto);
	
	//crud
	// 자신이 한 운동기록하기 (운동DB 데이터 사용)
	int insertSportRecord(SportRecordDto recordDto);
	
	//기록시 운동이 운동DB에 없을 경우
}

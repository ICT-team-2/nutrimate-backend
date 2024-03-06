package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecordMapper {
	
	int insertRecord(RecordDto dto);
	
	int updateRecord(RecordDto dto);
	
	int deleteRecord(@Param("recordId") int recordId);
	
	int findAlreadyDeletedRecord(int recordId);
}

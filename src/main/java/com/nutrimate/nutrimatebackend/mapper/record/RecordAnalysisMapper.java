package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.AnalyzeDateDto;
import com.nutrimate.nutrimatebackend.model.record.RecordAnalysisDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordAnalysisMapper {
	
	// 먹은 칼로리와 일일 권장 칼로리 열람하기
	List<RecordAnalysisDto> findRecordAnalysis(AnalyzeDateDto dto);
	
	
}

package com.nutrimate.nutrimatebackend.service.record;

import com.nutrimate.nutrimatebackend.mapper.record.RecordMapper;
import com.nutrimate.nutrimatebackend.mapper.record.SportDBMapper;
import com.nutrimate.nutrimatebackend.mapper.record.SportRecordMapper;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import com.nutrimate.nutrimatebackend.model.record.SportDto;
import com.nutrimate.nutrimatebackend.model.record.SportRecordDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class SportRecordService {
	
	private SportRecordMapper sportRecordMapper;
	private SportDBMapper sportDBMapper;
	private RecordMapper recordMapper;
	
	public SportRecordService(
			SportRecordMapper sportRecordMapper,
			SportDBMapper sportDBMapper, RecordMapper recordMapper) {
		this.sportRecordMapper = sportRecordMapper;
		this.sportDBMapper = sportDBMapper;
		this.recordMapper = recordMapper;
	}
	
	//운동 DB로부터 운동정보 가져오기
	//운동명으로 운동정보 가져오기
	public List<SportDto> findSportListBySearchWord(RecordPagingDto dto) {
		return sportDBMapper.findSportListBySearchWord(dto);
	}
	
	//sportId로 met값 가져오기
	public double findMetBySportId(int sportId) {
		return sportDBMapper.findMetBySportId(sportId);
	}
	
	public double convertMetToCalories(double met, double weight, int minutes) {
		return met * weight * minutes / 60;
	}
	
	//운동 기록 가져오기
	public List<SportRecordDto> findSportRecordByUserIdAndDoDate(RecordDto dto) {
		return sportRecordMapper.findSportRecordByUserIdAndDoDate(dto);
	}
	
	//운동 기록하기(DB)
	@Transactional
	public int insertSportRecordWithDB(SportRecordDto dto) {
		double sportCal = convertMetToCalories(
				findMetBySportId(dto.getSportId()),
				dto.getSportWeight(),
				dto.getSportTime());
		dto.setSportCal(sportCal);
		
		recordMapper.insertRecord(dto.getRecord());
		return sportRecordMapper.insertSportRecord(dto);
	}
	
	
	//기록 삭제하기
	public int deleteRecord(RecordDto dto) {
		return recordMapper.deleteRecord(dto.getRecordId());
	}
	public boolean isAlreadyDeletedRecord(int recordId) {
		return recordMapper.findAlreadyDeletedRecord(recordId) == 1;
	}
}

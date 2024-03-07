package com.nutrimate.nutrimatebackend.service.record;

import com.nutrimate.nutrimatebackend.mapper.record.DietRecordMapper;
import com.nutrimate.nutrimatebackend.mapper.record.FoodDBMapper;
import com.nutrimate.nutrimatebackend.mapper.record.RecordMapper;
import com.nutrimate.nutrimatebackend.model.record.FoodDto;
import com.nutrimate.nutrimatebackend.model.record.FoodRecordDto;
import com.nutrimate.nutrimatebackend.model.record.RecordDto;
import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class DietRecordService {
	
	private DietRecordMapper dietRecordMapper;
	private FoodDBMapper foodDBMapper;
	private RecordMapper recordMapper;
	
	public DietRecordService(
			DietRecordMapper dietRecordMapper,
			FoodDBMapper foodDBMapper, RecordMapper recordMapper) {
		this.dietRecordMapper = dietRecordMapper;
		this.foodDBMapper = foodDBMapper;
		this.recordMapper = recordMapper;
	}
	
	//foodDB로부터 음식정보 가져오기
	//음식명으로 음식정보 가져오기
	public List<FoodDto> findFoodListBySearchWord(RecordPagingDto dto) {
		return foodDBMapper.findFoodListBySearchWord(dto);
	}
	
	//자신이 등록한 음식정보 가져오기
	public List<FoodDto> findCustomFoodListBySearchWord(String searchWord, int userId) {
		return foodDBMapper.findCustomFoodListBySearchWord(searchWord, userId);
	}
	
	//foodId List로 음식정보 가져오기
	public List<FoodDto> findFoodListByFoodId(List<Integer> foodId) {
		return foodDBMapper.findFoodListByFoodId(foodId);
	}
	
	//식단 기록 가져오기
	public List<FoodRecordDto> findDietRecordByUserIdAndDoDate(RecordDto dto) {
		return dietRecordMapper.findDietRecordByUserIdAndDoDate(dto);
	}
	
	//식단 기록하기(DB)
	@Transactional
	public int insertFoodRecordWithDB(FoodRecordDto dto) {
		recordMapper.insertRecord(dto.getRecord());
		return dietRecordMapper.insertFoodRecord(dto);
	}
	//식단 기록하기(custom)
	@Transactional
	public int insertFoodRecordWithCustom(FoodRecordDto dto) {
		dto.setUserId(dto.getRecord().getUserId());
		
		foodDBMapper.insertFood(dto);//음식정보 저장
		dietRecordMapper.insertCustomFood(dto); //커스텀 음식테이블 저장
		recordMapper.insertRecord(dto.getRecord());//기록 저장
		return dietRecordMapper.insertFoodRecord(dto);//식단 기록 저장
	}
	
	//기록 삭제하기
	public int deleteRecord(RecordDto dto) {
		return recordMapper.deleteRecord(dto.getRecordId());
	}
	
	public boolean isAlreadyDeletedRecord(int recordId) {
		return recordMapper.findAlreadyDeletedRecord(recordId) == 1;
	}
}

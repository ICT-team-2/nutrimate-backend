package com.nutrimate.nutrimatebackend.mapper.record;

import com.nutrimate.nutrimatebackend.model.record.FoodDto;
import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodDBMapper {
	
	//db에 저장된 음식 정보 가져오기
	//read
	List<FoodDto> findFoodListByWithoutCustomSearchWord(RecordPagingDto dto);
	
	int findCountFoodListWithoutCustom();
	
	List<FoodDto> findFoodListByFoodId(@Param("foodId") List<Integer> foodId);
	
	//유저가 직접 입력한 음식DB 가져오기
	List<FoodDto> findCustomFoodListBySearchWord(
			RecordPagingDto dto);
	
	int findCountCustomFoodList(RecordPagingDto dto);
	
	//create
	int insertFood(FoodDto foodDto);
	
	//update (사용x)
//	int updateFood(FoodDto foodDto);
	
	//delete (사용x)
//	int deleteFood(@Param("foodId") int foodId);
	
}

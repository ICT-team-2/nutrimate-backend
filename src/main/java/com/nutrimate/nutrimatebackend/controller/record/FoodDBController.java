package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.FoodDto;
import com.nutrimate.nutrimatebackend.service.record.DietRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodDBController {
	
	private DietRecordService service;
	
	public FoodDBController(DietRecordService service) {
		this.service = service;
	}
	
	//식단 DB로부터 음식정보 가져오기
	//음식명으로 음식정보 가져오기
	@GetMapping("/keyword")
	public List<FoodDto> findFoodListBySearchWord(String searchWord) {
		return service.findFoodListBySearchWord(searchWord);
	}
	
	@GetMapping("/id")
	public List<FoodDto> findFoodListByFoodId(@RequestParam List<Integer> foodId) {
		return service.findFoodListByFoodId(foodId);
	}
	
	@GetMapping("/custom")
	public List<FoodDto> findCustomFoodListBySearchWord(
			String searchWord, int userId) {
		return service.findCustomFoodListBySearchWord(searchWord, userId);
	}
	
}

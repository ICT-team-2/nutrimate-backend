package com.nutrimate.nutrimatebackend.controller.record;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.model.record.FoodDto;
import com.nutrimate.nutrimatebackend.model.record.RecordPagingDto;
import com.nutrimate.nutrimatebackend.service.record.DietRecordService;

@RestController
@RequestMapping("/food")
public class FoodDBController {

    private DietRecordService service;
    private ObjectMapper objectMapper;

    public FoodDBController(DietRecordService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    // 식단 DB로부터 음식정보 가져오기
    // 음식명으로 음식정보 가져오기
    @GetMapping("/search")
    public Map<String, Object> findFoodListBySearchWord(RecordPagingDto dto) {
        int totalCount = service.countFoodListWithoutCustom(dto);
        dto.setTotalCount(totalCount);
        dto.setTotalPage((int) Math.ceil((double) totalCount / dto.getReceivePage()));

        Map<String, Object> map = objectMapper.convertValue(dto, Map.class);
        map.put("foodList", service.findFoodListBySearchWord(dto));
        return map;
    }

    @GetMapping("/id")
    public List<FoodDto> findFoodListByFoodId(@RequestParam("foodId") List<Integer> foodId) {
        return service.findFoodListByFoodId(foodId);
    }

    @GetMapping("/custom")
    public Map<String, Object> findCustomFoodListBySearchWord(RecordPagingDto dto) {
        int totalCount = service.countCustomFoodList(dto);
        dto.setTotalCount(totalCount);
        dto.setTotalPage((int) Math.ceil((double) totalCount / dto.getReceivePage()));

        Map<String, Object> map = objectMapper.convertValue(dto, Map.class);
        map.put("foodList", service.findCustomFoodListBySearchWord(dto));
        return map;
    }

}

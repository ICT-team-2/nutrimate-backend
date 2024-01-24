package com.nutrimate.nutrimatebackend.controller.infoboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.infoboard.DietDto;
import com.nutrimate.nutrimatebackend.model.infoboard.FoodboardDto;
import com.nutrimate.nutrimatebackend.service.infoboard.DietService;

@RestController
public class DietController {
	  @Autowired
	  private DietService dietService;
	  
	//게시글 전체 리스트
	  @GetMapping("/DietBoardList.do")
	  public List<DietDto> DietBoardList(@ModelAttribute DietDto dto) {
		  List<DietDto> dietList =dietService.selectListDietBoard(dto);
		  return dietList;
		  
	  }
	  
	 //게시글 상세 보기
	  @GetMapping("/DietBoardOne.do")
	  public List<FoodboardDto> DietBoardOne(@ModelAttribute DietDto dto) {
    	    if(dto.getBOARD() !=null && dto.getBOARD().equals("LIST") ) {
    	      dietService.saveViewCount(dto);
    	    }
    	    List<FoodboardDto> dietList =dietService.selectDietBoardOne(dto);
            return dietList;
		  
	  }
	  
	  //게시글 입력
	  @PostMapping("/WriteBoard.do")
	  public Map WriteBlock(@RequestBody DietDto dto,@RequestParam String userId) {
	       Map map = new HashMap();
	       if(dto.getFoodId()==null) {
	         map.put("WriteOK", "음식 사진을 올려주세요.");
	         return map;      
	       }
		   dto.setUserId(userId);
	   	   int affected= dietService.saveBoard(dto);
	   	   if(affected == 1) {
	   		   map.put("WriteOK", "게시물 입력을 성공했습니다.");
	   	   }else {
	   		   map.put("WriteOK", "게시물 입력을 실패했습니다!");
	   		   
	   	   }
	   	   return map;
      }
	  
	  //게시글 수정
	  @PutMapping("/EditBoard.do")
	  public Map EditBoard(@RequestBody DietDto dto,@RequestParam String userId,@RequestParam int boardId) {
		  Map map = new HashMap();
		  dto.setUserId(userId);
		  dto.setBoardId(boardId);
		  int affected= dietService.editBoard(dto);
		   if(affected == 1) {
				  map.put("EDITOK", "게시물 수정을 성공했습니다.");
	   	   }else {
	   		   map.put("EDITOK", "게시물 수정에 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	  
	  
	  
	//게시글 삭제
	  @PutMapping("/DeleteBoard.do")
	  public Map DeleteBoard(@ModelAttribute DietDto dto) {
		  Map map = new HashMap();
		  int affected= dietService.deleteBoard(dto);
		   if(affected == 1) {
				  map.put("DELETEOK", "게시물 삭제에 성공했습니다.");
	   	   }else {
	   		   map.put("DELETEOK", "게시물 삭제를 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	  //좋아요 입력 삭제
	  @PostMapping("/WriteLike.do")
      public Map WriteLike(@ModelAttribute DietDto dto) {
	        Map map = new HashMap();
    	    int count=dietService.countLike(dto);//
            if(count == 0) {
                int affected= dietService.saveLikeBoard(dto);
                 
            }else { 
              int affected= dietService.DeleteLikeBoard(dto);
            }
            return map;
      }
	  //이전글-다음글
	  @GetMapping("/DietPriev.do")
      public List<DietDto> DietPriev(@ModelAttribute DietDto dto) {
	      
	      DietDto prev = dietService.selectPrev(dto);
	      DietDto next = dietService.selectNext(dto);
	      System.out.println(prev);
	      List<DietDto> prevnextList=new ArrayList<>();
	      prevnextList.add(prev);
	      prevnextList.add(next);
          return prevnextList;//[0]이 이전글 [1]이 다음글
          
      }

}

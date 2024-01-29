package com.nutrimate.nutrimatebackend.controller.infoboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.nutrimate.nutrimatebackend.model.infoboard.DietDto;
import com.nutrimate.nutrimatebackend.model.infoboard.FileUtils;
import com.nutrimate.nutrimatebackend.service.infoboard.DietService;

@RestController
public class DietController {
	  @Autowired
	  private DietService dietService;
	  String phisicalPath="C://Temp/upload";
	  
	  
	//게시글 전체 리스트
	  @GetMapping("/infoboard/dietboards")
	  public List<DietDto> DietBoardList(@ModelAttribute DietDto dto) {
		  List<DietDto> dietList =dietService.selectListDietBoard(dto);
		  return dietList;
		  
	  }
	  

	  
	 //게시글 상세 보기,이전글다음글
	  @GetMapping("/infoboard/dietboards/{boardId}")
	  public List<DietDto> DietBoardOne(@ModelAttribute DietDto dto) {
    	    if(dto.getBOARD() !=null && dto.getBOARD().equals("LIST") ) {
    	      dietService.saveViewCount(dto);
    	    }
    	    DietDto prev = dietService.selectPrev(dto);
            DietDto next = dietService.selectNext(dto);
            DietDto dietOne =dietService.selectDietBoardOne(dto);//[0]이 이전글 [1]이 다음글 [2]상세보기
            System.out.println(dietOne.getFbImg());
            System.out.println(prev);
            List<DietDto> dietList=new ArrayList<>();
            dietList.add(prev);
            dietList.add(next);
            dietList.add(dietOne);
            
            return dietList;
		  
	  }
	  
	 //해당하는 보드의 해시태그네임 얻어오기
	  @GetMapping("/infoboard/hashtags/{boardId}")
	  public ResponseEntity<List<Map<String, Object>>> HashTagFind(@ModelAttribute DietDto dto) {
	    List<DietDto> hashtagList_ =dietService.selectHashTag(dto);
	    List<Map<String, Object>> hashtagList = new ArrayList<>();
	    for (DietDto hashtag : hashtagList_) {
	      Map<String, Object> hashtag_ = new HashMap<>();
	      hashtag_.put("tagName", hashtag.getTagName());
	      hashtag_.put("tagId", hashtag.getTagId());
	      hashtagList.add(hashtag_);
	    }
        
	    return new ResponseEntity<>(hashtagList, HttpStatus.OK);
      
     }
	  
	  
	  //게시글 입력
	  @PostMapping(value="/infoboard/dietboards",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)//@RequestBody
	  public Map WriteBlock(DietDto dto,@RequestParam String userId,HttpServletRequest req,List<MultipartFile> files) {
	       StringBuffer fileNames=new StringBuffer();
	       Map map = new HashMap();
	       if(dto.getFoodId()==null) {
	         map.put("WriteOK", "음식 사진을 올려주세요.");
	         return map;         
	       }

	       try { 
	         fileNames= FileUtils.upload(files, phisicalPath);
	         dto.setFbImg(fileNames.toString());
	       }catch(Exception e) {//파일용량 초과시
	         map.put("WriteOK", "게시물 입력을 실패했습니다!!");
	         return map;
             
           }
	       dto.setUserId(userId);
	       if(dto.getTagNameList()!=null) {	  
	         int affected=dietService.saveBoardANDHashBoardANDHashTag(dto);
	         if(affected == 1) {
               map.put("WriteOK", "게시물 입력을 성공했습니다.");
             }else {
                 FileUtils.deletes(fileNames, phisicalPath, ",");
                 map.put("WriteOK", "게시물 입력을 실패했습니다!");
                 return map;         
             }

	       }else {
    	   	   int affected= dietService.saveBoard(dto);
    	   	   if(affected == 1) {
    	   		   map.put("WriteOK", "게시물 입력을 성공했습니다.");
    	   	   }else {
    	   	       FileUtils.deletes(fileNames, phisicalPath, ",");
    	   		   map.put("WriteOK", "게시물 입력을 실패했습니다!");
    	   		   return map;
    	   		   
    	   	   }
	       }
	   	   return map;

      }

    //게시글 수정
	  @PutMapping(value="/infoboard/dietboards/{boardId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	  public Map EditBoard(DietDto dto,@RequestParam String userId,@RequestParam int boardId,HttpServletRequest req,List<MultipartFile> files) {
		  Map map = new HashMap();
		  StringBuffer fileNames=new StringBuffer();
		  dto.setUserId(userId);
		  dto.setBoardId(boardId);
		  if(files !=null) {
    		  try {
    		    
    		    DietDto titledto=dietService.selectDietBoardOne(dto);
    		    System.out.println(titledto);
                StringBuffer titledto_ = new StringBuffer(titledto.getFbImg());
                fileNames= FileUtils.upload(files, phisicalPath);
                FileUtils.deletes(titledto_, phisicalPath, ",");
                dto.setFbImg(fileNames.toString());
                
                
                }catch(Exception e) {//파일용량 초과시
                    map.put("EDITOK", "게시물 수정을 실패했습니다!");
                    return map;
            
                }
		  }
    	if(dto.getTagNameList()!=null) {
    		    int affected=dietService.editBoardANDHashBoardANDHashTag(dto);
    		    if(affected == 1) {
                  map.put("EDITOK", "게시물 수정을 성공했습니다.");
                }else {
                    FileUtils.deletes(fileNames, phisicalPath, ",");
                    map.put("EDITOK", "게시물 수정을 실패했습니다!");
                    return map;         
                }
    		   
          }else {
    		   int affected= dietService.editBoard(dto);
    		   if(affected == 1) {
    				  map.put("EDITOK", "게시물 수정을 성공했습니다.");
    	   	   }else {
    	   	       FileUtils.deletes(fileNames, phisicalPath, ",");
    	   		   map.put("EDITOK", "게시물 수정에 실패했습니다!!");
    	   		   
    	   	   }
          }
    		   return map;
    		  
	  }
	  
	  
	  
	//게시글 삭제
	  @PutMapping("/infoboard/dietboards-deleted/{boardId}")
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
	  @PostMapping("/infoboard/likeboard/{boardId}")
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
	  
	//북마크 입력 삭제
      @PostMapping("/infoboard/bookmark/{boardId}")
      public Map WriteBookmark(@ModelAttribute DietDto dto) {
            Map map = new HashMap();
            int count=dietService.countBookMark(dto);//
            if(count == 0) {
                int affected= dietService.saveBookMarkBoard(dto);
                System.out.println("입력");
                 
            }else { 
              int affected= dietService.DeleteBookMarkBoard(dto);
              System.out.println("삭제");
            }
            return map;
      }
      



	  
	  
	  
	  

}

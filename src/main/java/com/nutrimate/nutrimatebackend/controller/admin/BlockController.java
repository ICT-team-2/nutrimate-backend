package com.nutrimate.nutrimatebackend.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import com.nutrimate.nutrimatebackend.model.admin.ReportDto;
import com.nutrimate.nutrimatebackend.service.admin.BlockService;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class BlockController {
  
  @Autowired
  private BlockService blockService;
  
	  //한 게시글 or 댓글 당 한사람만 신고하도록 하기
	  @PostMapping("/WriteBlock.do")
	  public Map WriteBlock(@ModelAttribute BlockDto dto) {
		  Map map = new HashMap();
		  String searchKeyWord = dto.getSeachKeyWord();
	       if(searchKeyWord.equals("BOARD")) {
	         int count=blockService.countboarder(dto);//
	           if(count == 0) {
	        	   int affected= blockService.saveBoardReport(dto);
	        	   if(affected == 1) {
	        		   map.put("REPORTOK", "게시물 신고 완료했습니다.");
	        	   }else {
	        		   map.put("REPORTOK", "게시물 신고에 실패했습니다!");
	        		   
	        	   }
	             	
	           }else {
	             map.put("REPORTOK", "이미 신고한 게시물입니다.");
	           }
	           return map;
	       }else {
	    	   int count=blockService.countcomment(dto);
	    	   if(count == 0) {
	    		   int affected= blockService.saveCommentReport(dto);
	    		   if(affected == 1) {
	    			   map.put("REPORTOK", "댓글 신고 완료했습니다.");
	        	   }else {
	        		   map.put("REPORTOK", "댓글 신고에 실패했습니다!");
	        		   
	        	   }
	               	
	             }else {
	               map.put("REPORTOK", "이미 신고한 댓글입니다.");
	             }
	             return map;
	          
	       }
	  }
	  
	  //신고한 글 보기-게시글
	  @GetMapping("/BlockListBoard.do")
	  public List<BlockDto> BlockList(@ModelAttribute BlockDto dto) {
		  List<BlockDto> blockList =blockService.selectAllBoard(dto);
			return blockList;
		  
	  }
	  
	  
	  //신고한 글 보기-댓글
	  @GetMapping("/BlockListComment.do")
	  public List<BlockDto> CommentList(@ModelAttribute BlockDto dto) {
		  List<BlockDto> blockList =blockService.selectAllComment(dto);
		  
			return blockList;
		  
	  }
	  
	  
	//신고한 글 사유-게시글
	  @GetMapping("/BlockReasonBoard.do")
	  public List<ReportDto> BlockReasonBoard(@ModelAttribute BlockDto dto) {
		  List<ReportDto> blockList =blockService.selectBoardReport(dto);
			return blockList;
		  
	  }
	//신고한 글 사유-댓글
	  @GetMapping("BlockReasonCommet.do")
	  public List<ReportDto> BlockReasonCommet(@ModelAttribute BlockDto dto) {
		  List<ReportDto> blockList =blockService.selectCommentReport(dto);
			return blockList;
		  
	  }
	  
	//신고한 게시글 차단
	  @PutMapping("/BlockBoard.do")
	  public Map BlockBoard(@ModelAttribute BlockDto dto) {
		  Map map = new HashMap();
		  int affected= blockService.blockBoard(dto);
		   if(affected == 1) {
				  map.put("BLOCKOK", "게시글을 차단했습니다.");
	   	   }else {
	   		   map.put("BLOCKOK", "게시글 차단에 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	  
	//신고한 댓글 차단
	  @PutMapping("/BlockComment.do")
	  public Map BlockComment(@ModelAttribute BlockDto dto) {
		  Map map = new HashMap();
		  int affected= blockService.blockComment(dto);
		   if(affected == 1) {
				  map.put("BLOCKOK", "댓글을 차단했흡니다.");
	   	   }else {
	   		   map.put("BLOCKOK", "댓글 차단에 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	//신고한 게시글 차단 취소
	  @PutMapping("/BlockCancelBoard.do")
	  public Map BlockCancelBoard(@ModelAttribute BlockDto dto) {
		  Map map = new HashMap();
		  int affected= blockService.blockCancelBoard(dto);
		   if(affected == 1) {
				  map.put("BLOCKOK", "게시글 차단 취소했습니다.");
	   	   }else {
	   		   map.put("BLOCKOK", "게시글 차단 취소에 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	//신고한 댓글 차단 취소
	  @PutMapping("/BlockCancelComment.do")
	  public Map BlockCancelComment(@ModelAttribute BlockDto dto) {
		  Map map = new HashMap();
		  int affected= blockService.blockCancelComment(dto);
		   if(affected == 1) {
				  map.put("BLOCKOK", "댓글 차단 취소 했습니다.");
	   	   }else {
	   		   map.put("BLOCKOK", "댓글 차단 취소에 실패했습니다!");
	   		   
	   	   }
		   return map;
		  
	  }
	//게시글 신고 내역 삭제
	 @DeleteMapping("/DeleteBlockList.do")
	 public Map DeleteBlockList(@ModelAttribute BlockDto dto) {
       Map map = new HashMap();
       int affected= blockService.deleteBlockList(dto);
        if(affected == 1) {
               map.put("BLOCKOK", "게시글 신고 내역을 삭제 했습니다.");
        }else {
            map.put("BLOCKOK", "게시글 신고 내역 삭제에 실패했습니다!");
            
        }
        return map;
       
   }
	
   //댓글 신고 내역 삭제
	 
	 @DeleteMapping("/DeleteBlockListComment.do")
     public Map DeleteBlockListComment(@ModelAttribute BlockDto dto) {
       Map map = new HashMap();
       int affected= blockService.deleteBlockListComment(dto);
        if(affected == 1) {
               map.put("BLOCKOK", "댓글 신고 내역을 삭제 했습니다.");
        }else {
            map.put("BLOCKOK", "댓글 신고 내역 삭제에 실패했습니다!");
            
        }
        return map;
       
   }
	
	
	  

}
package com.nutrimate.nutrimatebackend.controller.admin;

import com.nutrimate.nutrimatebackend.model.admin.AdminReportDto;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import com.nutrimate.nutrimatebackend.service.admin.BlockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@RestController
public class BlockController {
	
	@Autowired
	private BlockService blockService;
	
	
	//신고한 글 보기-게시글
	@GetMapping("/block/list/board")
	public List<BlockDto> BlockList(@ModelAttribute BlockDto dto) {
		List<BlockDto> blockList = blockService.selectAllBoard(dto);
		return blockList;
		
	}
	
	
	//신고한 글 보기-댓글
	@GetMapping("/block/list/comment")
	public List<BlockDto> CommentList(@ModelAttribute BlockDto dto) {
		List<BlockDto> blockList = blockService.selectAllComment(dto);
		
		return blockList;
		
	}
	
	
	//신고한 글 사유-게시글
	@GetMapping("/block/reason/board")
	public List<AdminReportDto> BlockReasonBoard(@ModelAttribute BlockDto dto) {
		List<AdminReportDto> blockList = blockService.selectBoardReport(dto);
		return blockList;
		
	}
	//신고한 글 사유-댓글
	@GetMapping("/block/reason/comment")
	public List<AdminReportDto> BlockReasonCommet(@ModelAttribute BlockDto dto) {
		List<AdminReportDto> blockList = blockService.selectCommentReport(dto);
		return blockList;
		
	}
	
	//신고한 게시글 차단
	@PutMapping("/block/board")
	public Map BlockBoard(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.blockBoard(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "게시글을 차단했습니다.");
		} else {
			map.put("BLOCKOK", "게시글 차단에 실패했습니다!");
			
		}
		return map;
		
	}
	
	//신고한 댓글 차단
	@PutMapping("/block/comment")
	public Map BlockComment(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.blockComment(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "댓글을 차단했흡니다.");
		} else {
			map.put("BLOCKOK", "댓글 차단에 실패했습니다!");
			
		}
		return map;
		
	}
	//신고한 게시글 차단 취소
	@PutMapping("/block/cancel/board")
	public Map BlockCancelBoard(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.blockCancelBoard(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "게시글 차단 취소했습니다.");
		} else {
			map.put("BLOCKOK", "게시글 차단 취소에 실패했습니다!");
			
		}
		return map;
		
	}
	//신고한 댓글 차단 취소
	@PutMapping("/block/cancel/comment")
	public Map BlockCancelComment(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.blockCancelComment(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "댓글 차단 취소 했습니다.");
		} else {
			map.put("BLOCKOK", "댓글 차단 취소에 실패했습니다!");
			
		}
		return map;
		
	}
	//게시글 신고 내역 삭제
	@DeleteMapping("/block/list")
	public Map DeleteBlockList(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.deleteBlockList(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "게시글 신고 내역을 삭제 했습니다.");
		} else {
			map.put("BLOCKOK", "게시글 신고 내역 삭제에 실패했습니다!");
			
		}
		return map;
		
	}
	
	//댓글 신고 내역 삭제
	
	@DeleteMapping("/block/list/comment")
	public Map DeleteBlockListComment(@ModelAttribute BlockDto dto) {
		Map map = new HashMap();
		int affected = blockService.deleteBlockListComment(dto);
		if (affected == 1) {
			map.put("BLOCKOK", "댓글 신고 내역을 삭제 했습니다.");
		} else {
			map.put("BLOCKOK", "댓글 신고 내역 삭제에 실패했습니다!");
			
		}
		return map;
		
	}
	
	
}
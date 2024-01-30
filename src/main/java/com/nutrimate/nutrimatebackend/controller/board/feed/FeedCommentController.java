package com.nutrimate.nutrimatebackend.controller.board.feed;

import com.nutrimate.nutrimatebackend.model.board.feed.FeedCommentDto;
import com.nutrimate.nutrimatebackend.service.board.feed.FeedCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board/comments")
public class FeedCommentController {
	
	@Autowired
	private FeedCommentService feedCommentService;
	
	// 글번호에 따른 댓글+대댓글 목록 조회 (완료)
	// 입력 파라미터 : boardId
	// 출력 데이터 : commentDto의 정보
	@GetMapping("/list/{boardId}")
	public ResponseEntity<List<FeedCommentDto>> findCommentsByBoardId(
			@PathVariable(name = "boardId") int boardId) {
		List<FeedCommentDto> comments = feedCommentService.findCommentsByBoardId(boardId);
		List<FeedCommentDto> jsonResponse = new ArrayList<>();
		for (FeedCommentDto comment : comments) {
			if ("Y".equals(comment.getDeleted())) {
				if (1 < feedCommentService.countReplies(comment.getCmtId())) {
					// 삭제된 댓글이면서 대댓글이 없으면 출력하지 않음
					// 삭제된 댓글이면서 대댓글이 있는 경우 "삭제된 댓글입니다" 출력
					comment.setCmtContent("삭제된 댓글입니다");
					jsonResponse.add(comment);
				}
			} else {
				jsonResponse.add(comment);
			}
		}
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 대댓글 수 확인 (delete='Y'시 확인용) (완료)
	// 해당 댓글과 그 자식 댓글들의 총 개수를 반환 (대댓글이 없다면 1반환)
	// 입력 파라미터 : cmtId
	// 출력 데이터 : replyCount
	@GetMapping("/countreplies/{cmtId}")
	public ResponseEntity<Map<String, Integer>> countReplies(
			@PathVariable(name = "cmtId") int cmtId) {
		int replyCount = feedCommentService.countReplies(cmtId);
		Map<String, Integer> jsonResponse = new HashMap<>();
		jsonResponse.put("replyCount", replyCount);
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 댓글 작성 (완료)
	// 입력 데이터 : userId, boardId, cmtContent
	// 출력 데이터 : cmtId
	@PostMapping("/write")
	public ResponseEntity<Map<String, Integer>> createComment(@RequestBody FeedCommentDto feedCommentDto) {
		feedCommentService.insertComment(feedCommentDto);
		Map<String, Integer> jsonResponse = new HashMap<>();
		jsonResponse.put("cmtId", feedCommentDto.getCmtId());
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 대댓글 작성 (완료)
	// 입력 데이터 : userId, boardId, cmtContent, cmtId(부모의 cmtId)
	// 출력 데이터 : mycmtId
	@PostMapping("/write/replies")
	public ResponseEntity<Map<String, Integer>> createReply(@RequestBody FeedCommentDto feedCommentDto) {
		feedCommentService.insertReply(feedCommentDto);
		Map<String, Integer> jsonResponse = new HashMap<>();
		jsonResponse.put("cmtId", feedCommentDto.getMycmtId());
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 댓글/대댓글 수정 (완료)
	// 입력 데이터 : cmtContent, cmtId
	// 출력 데이터 : cmtId
	@PutMapping("/edit")
	public ResponseEntity<Map<String, Integer>> updateComment(@RequestBody FeedCommentDto feedCommentDto) {
		feedCommentService.updateComment(feedCommentDto);
		Map<String, Integer> jsonResponse = new HashMap<>();
		jsonResponse.put("cmtId", feedCommentDto.getCmtId());
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 댓글/대댓글 삭제 (완료)
	// 입력 데이터 : cmtId
	// 출력 데이터 :
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteComment(@RequestBody FeedCommentDto feedCommentDto) {
		int affectedRows = feedCommentService.deleteComment(feedCommentDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		if (affectedRows > 0) {
			jsonResponse.put("message", "comments deleted successfully");
			jsonResponse.put("cmtId", feedCommentDto.getCmtId());
			return ResponseEntity.ok(jsonResponse);
		} else {
			jsonResponse.put("message", "comments delete failed");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jsonResponse);
		}
	}
	
}

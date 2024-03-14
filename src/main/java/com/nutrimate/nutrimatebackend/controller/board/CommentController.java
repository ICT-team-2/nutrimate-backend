package com.nutrimate.nutrimatebackend.controller.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.board.CommentDto;
import com.nutrimate.nutrimatebackend.service.board.CommentService;

@RestController
@RequestMapping("/board/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 글번호에 따른 댓글+대댓글 목록 조회 (완료)
    // 입력 파라미터 : boardId
    // 출력 데이터 : commentDto의 정보
    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentDto>> findCommentsByBoardId(
            @PathVariable(name = "boardId") int boardId) {
        List<CommentDto> comments = commentService.findCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }

    // 주어진 댓글 ID에 대한 대댓글을 가져오는 도우미 메소드
    private List<CommentDto> getReplies(int parentCommentId) {
        List<CommentDto> replies = commentService.findRepliesByParentId(parentCommentId);
        List<CommentDto> jsonResponse = new ArrayList<>();
        for (CommentDto reply : replies) {
            // 삭제된 댓글에 대한 처리
            if ("Y".equals(reply.getDeleted())) {
                // 대댓글이 있는 경우에 대한 처리
                if (1 < commentService.countReplies(reply.getCmtId())) {
                    // 삭제된 댓글에 대댓글이 있는 경우는 "삭제된 댓글입니다"를 출력하고 대대댓글을 포함시킴
                    reply.setCmtContent("삭제된 댓글입니다");
                    reply.setReplies(getReplies(reply.getCmtId()));
                } else {
                    // 삭제된 댓글에 대댓글이 없는 경우는 출력하지 않음
                    continue;
                }
            }
            reply.setReplies(getReplies(reply.getCmtId()));
            jsonResponse.add(reply);
        }
        return jsonResponse;
    }

    // 대댓글 수 확인 (delete='Y'시 확인용) (완료)
    // 해당 댓글과 그 자식 댓글들의 총 개수를 반환 (대댓글이 없다면 1반환)
    // 입력 파라미터 : cmtId
    // 출력 데이터 : replyCount
    @GetMapping("/countreplies/{cmtId}")
    public ResponseEntity<Map<String, Integer>> countReplies(
            @PathVariable(name = "cmtId") int cmtId) {
        int replyCount = commentService.countReplies(cmtId);
        Map<String, Integer> jsonResponse = new HashMap<>();
        jsonResponse.put("replyCount", replyCount);
        return ResponseEntity.ok(jsonResponse);
    }

    // 댓글 작성 (완료)
    // 입력 데이터 : userId, boardId, cmtContent
    // 출력 데이터 : cmtId
    @PostMapping("/write")
    public ResponseEntity<Map<String, Integer>> createComment(@RequestBody CommentDto commentDto) {
        commentService.insertComment(commentDto);
        Map<String, Integer> jsonResponse = new HashMap<>();
        jsonResponse.put("cmtId", commentDto.getCmtId());
        return ResponseEntity.ok(jsonResponse);
    }

    // 대댓글 작성 (완료)
    // 입력 데이터 : userId, boardId, cmtContent, cmtId(부모의 cmtId)
    // 출력 데이터 : mycmtId
    @PostMapping("/write/replies")
    public ResponseEntity<Map<String, Integer>> createReply(@RequestBody CommentDto commentDto) {
        commentService.insertReply(commentDto);
        Map<String, Integer> jsonResponse = new HashMap<>();
        jsonResponse.put("cmtId", commentDto.getMycmtId());
        return ResponseEntity.ok(jsonResponse);
    }

    // 댓글/대댓글 수정 (완료)
    // 입력 데이터 : cmtContent, cmtId
    // 출력 데이터 : cmtId
    @PutMapping("/edit")
    public ResponseEntity<Map<String, Integer>> updateComment(@RequestBody CommentDto commentDto) {
        commentService.updateComment(commentDto);
        Map<String, Integer> jsonResponse = new HashMap<>();
        jsonResponse.put("cmtId", commentDto.getCmtId());
        return ResponseEntity.ok(jsonResponse);
    }

    // 댓글/대댓글 삭제 (완료)
    // 입력 데이터 : cmtId
    // 출력 데이터 :
    @PostMapping("/delete")
    public CommentDto deleteComment(@RequestBody CommentDto commentDto) {
        return commentService.deleteComment(commentDto);
    }

}

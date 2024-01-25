package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.member.CommentDto;
import com.nutrimate.nutrimatebackend.service.test.CommentService;

@RestController
@RequestMapping("/api/board/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  // 글번호에 따른 댓글+대댓글 목록 조회
  @GetMapping("/findComments/{boardId}")
  public List<CommentDto> findCommentsByBoardId(@PathVariable int boardId) {
    return commentService.findCommentsByBoardId(boardId);
  }

  // 대댓글 수 확인
  @GetMapping("/countReplies/{commentId}")
  public int countReplies(@PathVariable int commentId) {
    return commentService.countReplies(commentId);
  }

  // 댓글 작성
  @PostMapping("/createComment")
  public void createComment(@RequestBody CommentDto commentDto) {
    commentService.insertComment(commentDto);
  }

  // 대댓글 작성
  @PostMapping("/createReply")
  public void createReply(@RequestBody CommentDto commentDto) {
    commentService.insertReply(commentDto);
  }

  // 댓글/대댓글 수정
  @PutMapping("/updateComment")
  public void updateComment(@RequestBody CommentDto commentDto) {
    commentService.updateComment(commentDto);
  }

  // 댓글/대댓글 삭제
  @DeleteMapping("/deleteComment")
  public void deleteComment(@RequestBody CommentDto commentDto) {
    commentService.deleteComment(commentDto);
  }

}

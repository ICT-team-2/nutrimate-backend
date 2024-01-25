package com.nutrimate.nutrimatebackend.service.board;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.board.CommentMapper;
import com.nutrimate.nutrimatebackend.model.board.CommentDto;

@Service
public class CommentService {

  @Autowired
  private CommentMapper commentMapper;

  // 글번호에 따른 댓글+대댓글 목록 조회
  public List<CommentDto> findCommentsByBoardId(int boardId) {
    return commentMapper.findCommentsByBoardId(boardId);
  }

  // 대댓글 수 확인
  public int countReplies(int commentId) {
    return commentMapper.countReplies(commentId);
  }

  // 댓글 작성
  public void insertComment(CommentDto commentDto) {
    commentMapper.insertComment(commentDto);
  }

  // 대댓글 작성
  public void insertReply(CommentDto commentDto) {
    commentMapper.insertReply(commentDto);
  }

  // 댓글/대댓글 수정
  public void updateComment(CommentDto commentDto) {
    commentMapper.updateComment(commentDto);
  }

  // 댓글/대댓글 삭제
  public void deleteComment(CommentDto commentDto) {
    commentMapper.deleteComment(commentDto);
  }

}
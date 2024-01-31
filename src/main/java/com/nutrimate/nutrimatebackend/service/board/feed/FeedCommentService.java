package com.nutrimate.nutrimatebackend.service.board.feed;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.board.feed.FeedCommentMapper;
import com.nutrimate.nutrimatebackend.model.board.feed.FeedCommentDto;

@Service
public class FeedCommentService {

  @Autowired
  private FeedCommentMapper feedCommentMapper;

  // 글번호에 따른 댓글+대댓글 목록 조회
  public List<FeedCommentDto> findCommentsByBoardId(int boardId) {
    return feedCommentMapper.findCommentsByBoardId(boardId);
  }

  public List<FeedCommentDto> findRepliesByParentId(int parentCommentId) {
    return feedCommentMapper.findRepliesByParentId(parentCommentId);
  }

  // 대댓글 수 확인
  public int countReplies(int commentId) {
    return feedCommentMapper.countReplies(commentId);
  }

  // 댓글 작성
  public void insertComment(FeedCommentDto feedCommentDto) {
    feedCommentMapper.insertComment(feedCommentDto);
  }

  // 대댓글 작성
  public void insertReply(FeedCommentDto feedCommentDto) {
    feedCommentMapper.insertReply(feedCommentDto);
  }

  // 댓글/대댓글 수정
  public void updateComment(FeedCommentDto feedCommentDto) {
    feedCommentMapper.updateComment(feedCommentDto);
  }

  // 댓글/대댓글 삭제
  public int deleteComment(FeedCommentDto feedCommentDto) {
    return feedCommentMapper.deleteComment(feedCommentDto);
  }


}

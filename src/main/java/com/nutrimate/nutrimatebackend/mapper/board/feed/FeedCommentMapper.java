package com.nutrimate.nutrimatebackend.mapper.board.feed;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.board.feed.FeedCommentDto;

@Mapper
public interface FeedCommentMapper {

  // 글번호에 따른 댓글+대댓글 목록 조회
  List<FeedCommentDto> findCommentsByBoardId(int boardId);

  List<FeedCommentDto> findRepliesByParentId(int parentCommentId);

  // 대댓글 수 확인
  int countReplies(int commentId);

  // 댓글 작성
  void insertComment(FeedCommentDto feedCommentDto);

  // 대댓글 작성
  void insertReply(FeedCommentDto feedCommentDto);

  // 댓글/대댓글 수정
  void updateComment(FeedCommentDto feedCommentDto);

  // 댓글/대댓글 삭제
  int deleteComment(FeedCommentDto feedCommentDto);


}

package com.nutrimate.nutrimatebackend.mapper.board.feed;

import com.nutrimate.nutrimatebackend.model.board.feed.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
	
	// 글번호에 따른 댓글+대댓글 목록 조회
	List<CommentDto> findCommentsByBoardId(int boardId);
	
	// 대댓글 수 확인
	int countReplies(int commentId);
	
	// 댓글 작성
	void insertComment(CommentDto commentDto);
	
	// 대댓글 작성
	void insertReply(CommentDto commentDto);
	
	// 댓글/대댓글 수정
	void updateComment(CommentDto commentDto);
	
	// 댓글/대댓글 삭제
	int deleteComment(CommentDto commentDto);
	
}

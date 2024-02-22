package com.nutrimate.nutrimatebackend.service.board;

import com.nutrimate.nutrimatebackend.mapper.board.CommentMapper;
import com.nutrimate.nutrimatebackend.model.board.CommentDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	//List<CommentDto> reply에 대댓글을 설정
	public List<CommentDto> getCommentsWithReplies(List<CommentDto> allComments) {
		// commentId를 key로 가지고 CommentDto를 value로 가지는 맵을 생성합니다.
		Map<Integer, CommentDto> commentMap = new HashMap<>();
		for (CommentDto comment : allComments) {
			commentMap.put(comment.getCmtId(), comment);
		}
		// 대댓글을 부모 댓글의 reply 리스트에 추가합니다.
		for (CommentDto comment : allComments) {
			if (comment.getCmtRef() != null) {
				CommentDto parentComment = commentMap.get(comment.getCmtRef());
				if (parentComment != null) {
					parentComment.getReplies().add(comment);
				}
			}
		}
		// 최상위 댓글만 리스트에 남기고 나머지는 제거합니다.
		List<CommentDto> comments = new ArrayList<>();
		for (CommentDto comment : allComments) {
			if (comment.getCmtDepth() == 0) {
				comments.add(comment);
			}
		}
		return comments;
	}
	
	// 글번호에 따른 댓글+대댓글 목록 조회
	public List<CommentDto> findCommentsByBoardId(int boardId) {
		List<CommentDto> allComments = commentMapper.findCommentsByBoardId(boardId);
//		log.info("allComments: " + allComments);
		return getCommentsWithReplies(allComments);
	}
	
	public List<CommentDto> findRepliesByParentId(int parentCommentId) {
		return commentMapper.findRepliesByParentId(parentCommentId);
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
	public int deleteComment(CommentDto commentDto) {
		return commentMapper.deleteComment(commentDto);
	}
	
	
}

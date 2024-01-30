package com.nutrimate.nutrimatebackend.service.board.sport;

import com.nutrimate.nutrimatebackend.mapper.board.sport.SportCommentsMapper;
import com.nutrimate.nutrimatebackend.model.board.sport.SportCommentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportCommentsService {
	
	@Autowired
	private SportCommentsMapper commentsMapper;
	
	public List<SportCommentsDto> getAllComments() {
		return commentsMapper.selectAllComments();
	}
	
	public List<SportCommentsDto> getCommentsByBoardId(int boardId) {
		return commentsMapper.selectCommentsByBoardId(boardId);
	}
	
	public void createComment(SportCommentsDto sportCommentsDto) {
		commentsMapper.insertComment(sportCommentsDto);
	}
	
	public void updateComment(int cmtId, SportCommentsDto sportCommentsDto) {
		commentsMapper.updateCommentContent(cmtId, sportCommentsDto);
	}
	
	public void deleteComment(int cmtId) {
		commentsMapper.deleteComment(cmtId);
	}
	
}

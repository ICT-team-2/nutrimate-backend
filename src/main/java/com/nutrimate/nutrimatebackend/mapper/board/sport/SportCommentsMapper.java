package com.nutrimate.nutrimatebackend.mapper.board.sport;

import com.nutrimate.nutrimatebackend.model.board.sport.SportCommentsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SportCommentsMapper {
	List<SportCommentsDto> selectAllComments();
	
	List<SportCommentsDto> selectCommentsByBoardId(int boardId);
	
	int insertComment(SportCommentsDto sportCommentsDto);
	
	int updateCommentContent(@Param("cmtId") int cmtId, @Param("commentsDto") SportCommentsDto sportCommentsDto);
	
	int deleteComment(int cmtId);
}

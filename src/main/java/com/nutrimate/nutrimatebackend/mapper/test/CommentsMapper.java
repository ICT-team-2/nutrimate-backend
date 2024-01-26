package com.nutrimate.nutrimatebackend.mapper.test;

import com.nutrimate.nutrimatebackend.model.member.CommentsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentsMapper {
	List<CommentsDto> selectAllComments();
    List<CommentsDto> selectCommentsByBoardId(int boardId);
    int insertComment(CommentsDto commentsDto);
    int updateCommentContent(@Param("cmtId") int cmtId, @Param("commentsDto") CommentsDto commentsDto);
    int deleteComment(int cmtId);
}

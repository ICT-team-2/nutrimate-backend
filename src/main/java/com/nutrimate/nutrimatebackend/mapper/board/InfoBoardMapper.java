package com.nutrimate.nutrimatebackend.mapper.board;

import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.PagingDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InfoBoardMapper {
	
	//전체 게시글 목록 조회
	List<InfoBoardDto> findAll(PagingDto dto);
	
	int findCountAll(PagingDto dto);
	
	int updateViewCount(@Param("boardId") int boardId);
	
	int findRankByBoardId(
			@Param("boardId") int boardId,
			@Param("category") String category);
	
	Map<String, Integer> findPrevAndNextByBoardId(
			@Param("rank") int rank, @Param("category") String category);
}

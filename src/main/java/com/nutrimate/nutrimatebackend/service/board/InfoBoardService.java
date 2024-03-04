package com.nutrimate.nutrimatebackend.service.board;

import com.nutrimate.nutrimatebackend.mapper.board.InfoBoardMapper;
import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.PagingDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoBoardService {
	
	private InfoBoardMapper infoBoardMapper;
	
	public InfoBoardService(InfoBoardMapper infoBoardMapper) {
		this.infoBoardMapper = infoBoardMapper;
	}
	
	public List<InfoBoardDto> findAllInfoBoardList(PagingDto dto) {
		return infoBoardMapper.findAll(dto);
	}
	
	public int countAllInfoBoardList(PagingDto dto) {
		return infoBoardMapper.findCountAll(dto);
	}
	
	public int updateViewCount(int boardId) {
		return infoBoardMapper.updateViewCount(boardId);
	}
	
	public int findRankByBoardId(int boardId, String category) {
		return infoBoardMapper.findRankByBoardId(boardId, category);
	}
	
	public Map<String, Integer> findPrevAndNextByBoardId(int boardId, String category) {
		return infoBoardMapper.findPrevAndNextByBoardId(
				findRankByBoardId(boardId, category), category);
	}
}

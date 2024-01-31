package com.nutrimate.nutrimatebackend.service.board.sport;

import com.nutrimate.nutrimatebackend.mapper.board.sport.SportBoardMapper;
import com.nutrimate.nutrimatebackend.model.board.sport.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.board.sport.LikeDto;
import com.nutrimate.nutrimatebackend.model.board.sport.SportBoardDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class SportBoardService {
	
	@Autowired
	private SportBoardMapper sportBoardMapper;
	
	//게시글 생성
	@Transactional
	public SportBoardDto createBoard(SportBoardDto board) {
		sportBoardMapper.insertBoard(board);
		sportBoardMapper.insertSportBoard(board);
		// 해시태그 작성
		List<String> hashtags = board.getHashtag();
		if (hashtags == null) {
			return board;
		}
		for (String tag : hashtags) {
			int checkTagId = sportBoardMapper.checkTagId(tag);
			if (checkTagId == 0) {
				sportBoardMapper.insertTag(tag);
			} else {
				System.out.println("중복키 값이 있어요");
			}
			board.setTagName(tag);
			sportBoardMapper.insertHashtag(board);
		}
		return board;
	}
	
	//게시글 조회(상세)
	public SportBoardDto getBoard(int boardId) {
		return sportBoardMapper.selectBoard(boardId); //게시판 정보 조회
	}
	
	//게시글 조회(전체)
	public Map<String, Object> getBoards(int pageNum, String searchUser, String searchTitle, String searchContent, String searchTag) {
		int pageSize = 10; //페이지당 보여줄 게시글의 수
		List<SportBoardDto> boards = sportBoardMapper.selectAllBoards(pageNum, pageSize, searchUser, searchTitle, searchContent, searchTag);
		log.info("boards: " + boards);
		int totalPosts = sportBoardMapper.countBoards(); //총 게시글 수
		int totalPage = (totalPosts % pageSize == 0) ? totalPosts / pageSize : totalPosts / pageSize + 1; //총 페이지 수를 계산
		
		Map<String, Object> response = new HashMap<>();
		response.put("pageNum", pageNum);
		response.put("totalPage", totalPage);
		response.put("boards", boards);
		
		return response;
	}
	
	//게시글 수정
	@Transactional
	public SportBoardDto updateBoard(SportBoardDto board) {
		sportBoardMapper.updateBoard(board);
		sportBoardMapper.updateSportBoard(board);
		sportBoardMapper.updateHashtag(board);
		// 해당 글의 해시태그 삭제 후 작성
		List<String> hashtags = board.getHashtag();
		for (String tag : hashtags) {
			int checkTagId = sportBoardMapper.checkTagId(tag);
			if (checkTagId == 0) {
				sportBoardMapper.insertTag(tag);
			} else {
				System.out.println("중복키 값이 있어요");
			}
			board.setTagName(tag);
			sportBoardMapper.insertHashtag(board);
		}
		return board;
	}
	
	//게시글 삭제
	public void deleteBoard(int boardId) {
		sportBoardMapper.deleteBoard(boardId);
	}
	
	//좋아요 수 조회
	public int countLikes(String boardId) {
		return sportBoardMapper.countLikes(boardId);
	}
	
	//좋아요 생성
	public boolean insertLike(LikeDto likeDto) {
		return sportBoardMapper.insertLike(likeDto) == 1;
	}
	
	//좋아요 삭제
	public int deleteLike(LikeDto likeDto) {
		return sportBoardMapper.deleteLike(likeDto);
	}
	
	//조회수 증가
	public void updateBoardViewcount(int boardId) {
		sportBoardMapper.updateBoardViewcount(boardId);
	}
	
	//이전글
	public SportBoardDto getPrevBoard(int boardId) {
		SportBoardDto board = sportBoardMapper.selectPrevBoard(boardId);
		if (board != null) {
			sportBoardMapper.updateBoardViewcount(board.getBoardId());
		}
		return board;
	}
	
	//다음글
	public SportBoardDto getNextBoard(int boardId) {
		SportBoardDto board = sportBoardMapper.selectNextBoard(boardId);
		if (board != null) {
			sportBoardMapper.updateBoardViewcount(board.getBoardId());
		}
		return board;
	}
	
	//북마크 저장 여부 확인
	public boolean isBookmarked(BookmarkDto bookmarkDto) {
	    return sportBoardMapper.countBookmarks(bookmarkDto) > 0;
	}

	//북마크 생성
	public boolean insertBookmark(BookmarkDto bookmarkDto) {
		return sportBoardMapper.insertBookmark(bookmarkDto) == 1;
	}
	
	//북마크 삭제
	public int deleteBookmark(BookmarkDto bookmarkDto) {
		return sportBoardMapper.deleteBookmark(bookmarkDto);
	}
	
	//해당 글의 해시태그 가져오기
	public List<SportBoardDto> findHashtagsByBoardId(SportBoardDto board) {
		return sportBoardMapper.findHashtagsByBoardId(board);
	}

}

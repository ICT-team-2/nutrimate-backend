package com.nutrimate.nutrimatebackend.service.test;

import com.nutrimate.nutrimatebackend.mapper.test.BoardMapper;
import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	//게시글 생성
	@Transactional
	public BoardDto createBoard(BoardDto board) {
		boardMapper.insertBoard(board);
		boardMapper.insertSportBoard(board);
		// 해시태그 작성
	    List<String> hashtags = board.getHashtag();
	    for (String tag : hashtags) {
	      int checkTagId = boardMapper.checkTagId(tag);
	      if (checkTagId == 0) {
	    	  boardMapper.insertTag(tag);
	      } else {
	        System.out.println("중복키 값이 있어요");
	      }
	      board.setTagName(tag);
	      boardMapper.insertHashtag(board);
	    }
		return board;
	}
	
	//게시글 조회(상세)
	public BoardDto getBoard(int boardId) {
		return boardMapper.selectBoard(boardId); //게시판 정보 조회
	}
	
	//게시글 조회(전체)
	public Map<String, Object> getBoards(int pageNum, String searchUser, String searchTitle, String searchTag) {
		int pageSize = 10; //페이지당 보여줄 게시글의 수
		List<BoardDto> boards = boardMapper.selectAllBoards(pageNum, pageSize, searchUser, searchTitle, searchTag);
		log.info("boards: " + boards);
		int totalPosts = boardMapper.countBoards(); //총 게시글 수
		int totalPage = (totalPosts % pageSize == 0) ? totalPosts / pageSize : totalPosts / pageSize + 1; //총 페이지 수를 계산
		
		Map<String, Object> response = new HashMap<>();
		response.put("pageNum", pageNum);
		response.put("totalPage", totalPage);
		response.put("boards", boards);
		
		return response;
	}
	
	//게시글 수정
	@Transactional
	public BoardDto updateBoard(BoardDto board) {
		boardMapper.updateBoard(board);
		boardMapper.updateSportBoard(board);
		boardMapper.updateHashtag(board);
		// 해당 글의 해시태그 삭제 후 작성
	    List<String> hashtags = board.getHashtag();
	    for (String tag : hashtags) {
	      int checkTagId = boardMapper.checkTagId(tag);
	      if (checkTagId == 0) {
	    	  boardMapper.insertTag(tag);
	      } else {
	        System.out.println("중복키 값이 있어요");
	      }
	      board.setTagName(tag);
	      boardMapper.insertHashtag(board);
	    }
		return board;
	}
	
	//게시글 삭제
	public void deleteBoard(int boardId) {
		boardMapper.deleteBoard(boardId);
	}
	
	//좋아요 수 조회
	public int countLikes(String boardId) {
		return boardMapper.countLikes(boardId);
	}
	
	//좋아요 생성
	public boolean insertLike(LikeDto likeDto) {
		return boardMapper.insertLike(likeDto) == 1;
	}
	
	//좋아요 삭제
	public int deleteLike(LikeDto likeDto) {
		return boardMapper.deleteLike(likeDto);
	}
	
	//조회수 증가
	public void updateBoardViewcount(int boardId) {
		boardMapper.updateBoardViewcount(boardId);
	}

	//이전글
	public BoardDto getPrevBoard(int boardId) {
		BoardDto board = boardMapper.selectPrevBoard(boardId);
		if (board != null) {
			boardMapper.updateBoardViewcount(board.getBoardId());
		}
		return board;
	}
	
	//다음글
	public BoardDto getNextBoard(int boardId) {
		BoardDto board = boardMapper.selectNextBoard(boardId);
		if (board != null) {
			boardMapper.updateBoardViewcount(board.getBoardId());
		}
		return board;
	}

	//북마크 생성
	public boolean insertBookmark(BookmarkDto bookmarkDto) {
		return boardMapper.insertBookmark(bookmarkDto) == 1;
	}

	//북마크 삭제
	public int deleteBookmark(BookmarkDto bookmarkDto) {
		return boardMapper.deleteBookmark(bookmarkDto);
	}
	
	// 해당 글의 해시태그 가져오기
    public List<BoardDto> findHashtagsByBoardId(BoardDto board) {
      return boardMapper.findHashtagsByBoardId(board);
    }

    // 해시태그로 글 검색
    /*
    public List<BoardDto> findBoardsByTagName(BoardDto board) {
      return boardMapper.findBoardsByTagName(board);
    }
    */
}

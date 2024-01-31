package com.nutrimate.nutrimatebackend.mapper.board.sport;

import com.nutrimate.nutrimatebackend.model.board.sport.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.board.sport.LikeDto;
import com.nutrimate.nutrimatebackend.model.board.sport.SportBoardDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SportBoardMapper {
	//게시글 생성
	boolean insertBoard(SportBoardDto board);
	
	//게시글 조회(상세)
	SportBoardDto selectBoard(int boardId);
	
	//모든 게시글 조회(전체)
	List<SportBoardDto> selectAllBoards(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("searchUser") String searchUser, @Param("searchTitle") String searchTitle, @Param("searchContent") String searchContent, @Param("searchTag") String searchTag);
	
	//게시글 수정
	int updateBoard(SportBoardDto board);
	
	//게시글 삭제
	int deleteBoard(int boardId);
	
	//스포츠 게시판 게시글 생성
	int insertSportBoard(SportBoardDto board);
	
	//스포츠 게시판 게시글 수정
	int updateSportBoard(SportBoardDto board);
	
	//좋아요 수 조회
	int countLikes(String boardId);
	
	//좋아요 생성
	int insertLike(LikeDto likeDto);
	
	//좋아요 삭제
	int deleteLike(LikeDto likeDto);
	
	//조회수 증가
	int updateBoardViewcount(int boardId);
	
	//이전글/다음글
	SportBoardDto selectPrevBoard(int boardId); //이전글 조회
	
	SportBoardDto selectNextBoard(int boardId); //다음글 조회
	
	//총 페이지 수
	int countBoards();
	
	//북마크 저장여부 확인시
	int countBookmarks(BookmarkDto bookmarkDto);
	
	//북마크 추가
	int insertBookmark(BookmarkDto bookmarkDto);
	
	//북마크 삭제
	int deleteBookmark(BookmarkDto bookmarkDto);
	
	//해당 글의 해시태그 가져오기
	List<SportBoardDto> findHashtagsByBoardId(SportBoardDto board);
	
	//해시태그 입력시
	int checkTagId(String tag);	
	void insertTag(String tag);	
	void insertHashtag(SportBoardDto board);
	
	//해시태그 수정시
	void updateHashtag(SportBoardDto board);
	
}

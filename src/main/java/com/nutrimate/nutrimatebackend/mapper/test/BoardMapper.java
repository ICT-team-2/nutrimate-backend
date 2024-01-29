package com.nutrimate.nutrimatebackend.mapper.test;

import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BoardMapper {
    //게시글 생성
    boolean insertBoard(BoardDto board);
    //게시글 조회(상세)
    BoardDto selectBoard(int boardId);
    //모든 게시글 조회(전체)
    List<BoardDto> selectAllBoards(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("searchUser") String searchUser, @Param("searchTitle") String searchTitle, @Param("searchTag") String searchTag);
    //게시글 수정
    int updateBoard(BoardDto board);
    //게시글 삭제
    int deleteBoard(int boardId);
    //스포츠 게시판 게시글 생성
    int insertSportBoard(BoardDto board);
    //스포츠 게시판 게시글 수정
    int updateSportBoard(BoardDto board);
    //좋아요 수 조회
    int countLikes(String boardId);
    //좋아요 생성
    int insertLike(LikeDto likeDto);
    //좋아요 삭제
    int deleteLike(LikeDto likeDto);
    //조회수 증가
    int updateBoardViewcount(int boardId);
    //검색
    //List<BoardDto> searchBoards(@Param("type") String type, @Param("keyword") String keyword);   
    //이전글/다음글
    BoardDto selectPrevBoard(int boardId); //이전글 조회
    BoardDto selectNextBoard(int boardId); //다음글 조회  
    //총 페이지 수
    int countBoards();    
    //북마크
	int insertBookmark(BookmarkDto bookmarkDto);
	int deleteBookmark(BookmarkDto bookmarkDto);
	//해당 글의 해시태그 가져오기
	List<BoardDto> findHashtagsByBoardId(BoardDto board);
	//해시태그로 글 검색
	//List<BoardDto> findBoardsByTagName(BoardDto board);
	//해시태그 입력시
	int checkTagId(String tag);
	void insertTag(String tag);
	void insertHashtag(BoardDto board);
	//해시태그 수정시
	void updateHashtag(BoardDto board);
}

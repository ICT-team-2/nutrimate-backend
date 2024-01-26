package com.nutrimate.nutrimatebackend.mapper.test;

import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BoardMapper {
    //게시글 생성
    boolean insertBoard(BoardDto board);
    //게시글 조회
    BoardDto selectBoard(int boardId);
    //모든 게시글 조회
    //List<BoardDto> selectAllBoards(BoardDto board);
    List<BoardDto> selectAllBoards(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize, @Param("searchUser") String searchUser, @Param("searchTitle") String searchTitle);

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
    List<BoardDto> searchBoards(@Param("type") String type, @Param("keyword") String keyword);
    
    //이전글/다음글
    BoardDto selectPrevBoard(int boardId); //이전글 조회
    BoardDto selectNextBoard(int boardId); //다음글 조회
   
    //총 페이지 수
    int countBoards();

}

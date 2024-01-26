package com.nutrimate.nutrimatebackend.service.test;

import com.nutrimate.nutrimatebackend.mapper.test.BoardMapper;
import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Transactional
    public BoardDto createBoard(BoardDto board) {
        boardMapper.insertBoard(board);
        boardMapper.insertSportBoard(board);
        return board;
    }

    public BoardDto getBoard(int boardId) {
        return boardMapper.selectBoard(boardId); //게시판 정보 조회 
    }

    /*
    public List<BoardDto> getAllBoards(BoardDto board) {
        return boardMapper.selectAllBoards(board);
    }
    */
    public Map<String, Object> getBoards(int pageNum, String searchUser, String searchTitle) {
        int pageSize = 10; //페이지당 보여줄 게시글의 수
        List<BoardDto> boards = boardMapper.selectAllBoards(pageNum, pageSize, searchUser, searchTitle);
        int totalPosts = boardMapper.countBoards(); //총 게시글 수
        int totalPage = (totalPosts % pageSize == 0) ? totalPosts / pageSize : totalPosts / pageSize + 1; //총 페이지 수를 계산

        Map<String, Object> response = new HashMap<>();
        response.put("pageNum", pageNum);
        response.put("totalPage", totalPage);
        response.put("boards", boards);

        return response;
    }

    @Transactional
    public BoardDto updateBoard(BoardDto board) {
        boardMapper.updateBoard(board);
        boardMapper.updateSportBoard(board);
        return board;
    }

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
    
    //검색
    public List<BoardDto> searchBoards(String type, String keyword) {
        return boardMapper.searchBoards(type, "%" + keyword + "%");
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

}

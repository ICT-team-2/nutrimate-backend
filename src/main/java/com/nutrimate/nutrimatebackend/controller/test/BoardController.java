package com.nutrimate.nutrimatebackend.controller.test;

import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.FileUtils;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;
import com.nutrimate.nutrimatebackend.service.test.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	//글 생성
	@PostMapping()
	public BoardDto createBoard(BoardDto board, HttpServletRequest req, List<MultipartFile> files) {
		String phisicalPath = "C:\\Temp\\upload";
		StringBuffer fileNames = new StringBuffer();
		try {
			fileNames = FileUtils.upload(files, phisicalPath);
			board.setMapImg(fileNames.toString());
			
		} catch (Exception e) { //파일용량 초과시
			System.out.println("문제생김");
		}
		return boardService.createBoard(board);
	}
	
	//글 조회(상세)
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getBoardWithPrevAndNext(@PathVariable("id") int id) {
		Map<String, Object> response = new HashMap<>();
		BoardDto board = boardService.getBoard(id);
		if (board != null) {
			boardService.updateBoardViewcount(id); //조회수 증가
			response.put("current", board);
		}
		
		BoardDto prevBoard = boardService.getPrevBoard(id);
		if (prevBoard != null) {
			response.put("prev", prevBoard);
		}
		
		BoardDto nextBoard = boardService.getNextBoard(id);
		if (nextBoard != null) {
			response.put("next", nextBoard);
		}
		
		return response.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	//글 목록 조회(전체)
    /*
    @GetMapping
    public List<BoardDto> getAllBoards(@ModelAttribute BoardDto board) {
        return boardService.getAllBoards(board);
    }
    */
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllBoards(
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(required = false) String searchUser,
			@RequestParam(required = false) String searchTitle) {
		
		Map<String, Object> boards = boardService.getBoards(pageNum, searchUser, searchTitle);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}
	
	//글 수정
	@PutMapping("/{id}")
	public BoardDto updateBoard(@PathVariable("id") int id, @RequestBody BoardDto board) {
		board.setBoardId(id);
		return boardService.updateBoard(board);
	}
	
	//글 삭제
	@DeleteMapping("/{id}")
	public void deleteBoard(@PathVariable("id") int id) {
		boardService.deleteBoard(id);
	}
	
	//글 검색
	@GetMapping("/search")
	public List<BoardDto> searchBoards(@RequestParam("type") String type, @RequestParam("keyword") String keyword) {
		return boardService.searchBoards(type, keyword);
	}
	
	
	//좋아요 수 조회
	@GetMapping("/{boardId}/likes")
	public int countLikes(@PathVariable String boardId) {
		return boardService.countLikes(boardId);
	}
	
	//좋아요 생성
	@PostMapping("/{boardId}/likes")
	public ResponseEntity<Void> insertLike(@PathVariable String boardId, @RequestBody LikeDto likeDto) {
		likeDto.setBoardId(boardId);
		boolean isCreated = boardService.insertLike(likeDto);
		
		if (isCreated) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//좋아요 삭제
	@DeleteMapping("/{boardId}/likes")
	public ResponseEntity<Void> deleteLike(@PathVariable String boardId, @RequestBody LikeDto likeDto) {
		likeDto.setBoardId(boardId);
		int affectedRows = boardService.deleteLike(likeDto);
		
		if (affectedRows > 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
   
    /*
    //이전글 조회
    @GetMapping("/{id}/prev")
    public ResponseEntity<BoardDto> getPrevBoard(@PathVariable("id") int id) {
        BoardDto prevBoard = boardService.getPrevBoard(id);
        if (prevBoard != null) {
            return new ResponseEntity<>(prevBoard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //다음글 조회
    @GetMapping("/{id}/next")
    public ResponseEntity<BoardDto> getNextBoard(@PathVariable("id") int id) {
        BoardDto nextBoard = boardService.getNextBoard(id);
        if (nextBoard != null) {
            return new ResponseEntity<>(nextBoard, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    */
	
}

package com.nutrimate.nutrimatebackend.controller.test;

import com.nutrimate.nutrimatebackend.model.member.BoardDto;
import com.nutrimate.nutrimatebackend.model.member.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.member.FileUtils;
import com.nutrimate.nutrimatebackend.model.member.LikeDto;
import com.nutrimate.nutrimatebackend.service.test.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
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
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllBoards(
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(required = false) String searchUser,
			@RequestParam(required = false) String searchTitle,
			@RequestParam(required = false) String searchTag) {
		
		Map<String, Object> boards = boardService.getBoards(pageNum, searchUser, searchTitle, searchTag);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}
	
	//글 수정
	@PutMapping("/{id}")
	public BoardDto updateBoard(@PathVariable("id") int id, BoardDto board, HttpServletRequest req, List<MultipartFile> files) {
		board.setBoardId(id);
		String phisicalPath = "C:\\Temp\\upload";
		StringBuffer fileNames=new StringBuffer();
		if(files!=null) {
		try {
		    		   
			BoardDto titledto=boardService.getBoard(id);
		    System.out.println(titledto);
            StringBuffer titledto_ = new StringBuffer(titledto.getMapImg());
            fileNames= FileUtils.upload(files, phisicalPath);
            FileUtils.deletes(titledto_, phisicalPath, ",");
            board.setMapImg(fileNames.toString());
            }catch(Exception e) {//파일용량 초과시
                
            }
		}
		return boardService.updateBoard(board);
	}
	
	//글 삭제
	@DeleteMapping("/{id}")
	public void deleteBoard(@PathVariable("id") int id) {
		boardService.deleteBoard(id);
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
	
	//북마크 생성
	@PostMapping("/{boardId}/bookmark")
	public ResponseEntity<Void> insertBookmark(@PathVariable String boardId, @RequestBody BookmarkDto bookmarkDto) {
		bookmarkDto.setBoardId(boardId);
		boolean isCreated = boardService.insertBookmark(bookmarkDto);
		
		if (isCreated) {
			return new ResponseEntity<>(HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//북마크 삭제
	@DeleteMapping("/{boardId}/bookmark")
	public ResponseEntity<Void> deleteBookmark(@PathVariable String boardId, @RequestBody BookmarkDto bookmarkDto) {
		bookmarkDto.setBoardId(boardId);
		int affectedRows = boardService.deleteBookmark(bookmarkDto);
		
		if (affectedRows > 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/** 해시태그 **/
	// 해당 글의 해시태그 가져오기 (완료)
	// 입력 데이터 : boardId
	// 출력 데이터 : tagName or message(해시태그가 비어있거나 없는 게시글번호)
	@GetMapping("/hashtag")
	public ResponseEntity<List<Map<String, Object>>> findHashtagsByBoardId(@RequestBody BoardDto board) {
		List<BoardDto> tagNames = boardService.findHashtagsByBoardId(board);
	    List<Map<String, Object>> tagNameList = new ArrayList<>();
	    for (BoardDto tagName : tagNames) {
	      Map<String, Object> tagNameBoard = new HashMap<>();
	      tagNameBoard.put("tagName", tagName.getTagName());
	      tagNameList.add(tagNameBoard);
	    }
	    if (tagNameList.isEmpty()) {
	      Map<String, Object> tagNameBoard = new HashMap<>();
	      tagNameBoard.put("message", "해시태그가 없어요");
	      tagNameList.add(tagNameBoard);
	      return new ResponseEntity<>(tagNameList, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(tagNameList, HttpStatus.OK);
	}

	// 해시태그로 글 검색 (완료)
	// 입력 데이터 : tagName
	// 출력 데이터 : BoardList or message(해당 해시태그로 검색된 글이 없음)
	/*
	@GetMapping("/hashtag/search")
	public ResponseEntity<List<Map<String, Object>>> findBoardsByTagName(@RequestBody BoardDto board) {
	    List<BoardDto> findBoardsByTagNames = boardService.findBoardsByTagName(board);
	    int boardId = board.getBoardId();
	    List<Map<String, Object>> BoardsByTagNameList = new ArrayList<>();
	    for (BoardDto findBoardsByTagName : findBoardsByTagNames) {
	      Map<String, Object> BoardList = new HashMap<>();
	      BoardList.put("BoardList", findBoardsByTagName.getBoardId());
	      BoardsByTagNameList.add(BoardList);
	    }
	    if (BoardsByTagNameList.isEmpty()) {
	      Map<String, Object> tagNameFeed = new HashMap<>();
	      tagNameFeed.put("message", "해당 해시태그로 검색된 글이 없어요");
	      BoardsByTagNameList.add(tagNameFeed);
	      return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
	}
	*/
}

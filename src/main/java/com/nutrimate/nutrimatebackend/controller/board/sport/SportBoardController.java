package com.nutrimate.nutrimatebackend.controller.board.sport;

import com.nutrimate.nutrimatebackend.model.FileUtils;
import com.nutrimate.nutrimatebackend.model.board.sport.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.board.sport.LikeDto;
import com.nutrimate.nutrimatebackend.model.board.sport.SportBoardDto;
import com.nutrimate.nutrimatebackend.service.board.sport.SportBoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/boards/sport")
public class SportBoardController {
	
	@Autowired
	private SportBoardService sportBoardService;
	
	//글 생성
	@PostMapping()
	public Map<String, String> createBoard(SportBoardDto board, HttpServletRequest req, @RequestParam(required = false) List<MultipartFile> files) {
	    String phisicalPath = "C:\\Temp\\upload";
	    StringBuffer fileNames = new StringBuffer();
	    Map<String, String> map = new HashMap<>();
	    if (files == null || files.isEmpty()) {
	        map.put("message", "사진을 올려주세요");
	        return map;
	    }

	    try {
	        fileNames = FileUtils.upload(files, phisicalPath);
	        board.setMapImg(fileNames.toString());
	    } catch (Exception e) { //파일용량 초과시
	        System.out.println("문제생김");
	        map.put("message", "파일 업로드에 문제가 발생했습니다");
	        return map;
	    }

	    SportBoardDto createdBoard = sportBoardService.createBoard(board);

	    if (createdBoard == null) {
	        map.put("message", "글 작성에 실패했습니다");
	        return map;
	    } else {
	        map.put("message", "글 작성에 성공했습니다");
	        return map;
	    }
	}
	
	//글 조회(상세)
	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> getBoardWithPrevAndNext(@PathVariable("id") int id) {
		Map<String, Object> response = new HashMap<>();
		SportBoardDto board = sportBoardService.getBoard(id);
		if (board != null) {
			sportBoardService.updateBoardViewcount(id); //조회수 증가
			response.put("current", board);
		}
		
		SportBoardDto prevBoard = sportBoardService.getPrevBoard(id);
		if (prevBoard != null) {
			response.put("prev", prevBoard);
		}
		
		SportBoardDto nextBoard = sportBoardService.getNextBoard(id);
		if (nextBoard != null) {
			response.put("next", nextBoard);
		}
		
		return response.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(response, HttpStatus.OK);
	}	
	
	//글 목록 조회(전체)
	@GetMapping()
	public ResponseEntity<Map<String, Object>> getAllBoards(
			@RequestParam(name="pageNum",defaultValue = "1") int pageNum,
			@RequestParam(name="searchUser",required = false) String searchUser,
			@RequestParam(name="searchTitle",required = false) String searchTitle,
			@RequestParam(name="searchContent",required = false) String searchContent,
			@RequestParam(name="searchTag",required = false) String searchTag) {
		
		Map<String, Object> boards = sportBoardService.getBoards(pageNum, searchUser, searchTitle, searchContent, searchTag);
		return new ResponseEntity<>(boards, HttpStatus.OK);
	}
	
	//글 수정
	@PutMapping("/{id}")
	public Map<String, String> updateBoard(@PathVariable("id") int id, SportBoardDto board, HttpServletRequest req, @RequestParam("files") List<MultipartFile> files) {
	    board.setBoardId(id);
	    String phisicalPath = "C:\\Temp\\upload";
	    StringBuffer fileNames = new StringBuffer();
	    Map<String, String> map = new HashMap<>();

	    if (files != null) {
	        try {
	            SportBoardDto titledto = sportBoardService.getBoard(id);
	            System.out.println(titledto);
	            StringBuffer titledto_ = new StringBuffer(titledto.getMapImg());
	            fileNames = FileUtils.upload(files, phisicalPath);
	            FileUtils.deletes(titledto_, phisicalPath, ",");
	            board.setMapImg(fileNames.toString());
	        } catch (Exception e) {//파일용량 초과시
	            map.put("message", "파일 업로드에 문제가 발생했습니다");
	            return map;
	        }
	    }

	    SportBoardDto updatedBoard = sportBoardService.updateBoard(board);
	    
	    if (updatedBoard == null) {
	        map.put("message", "글 수정에 실패했습니다");
	        return map;
	    } else {
	        map.put("message", "글 수정에 성공했습니다");
	        return map;
	    }
	}
	
	//글 삭제
	@DeleteMapping("/{id}")
	public Map<String, String> deleteBoard(@PathVariable("id") int id) {
	    Map<String, String> map = new HashMap<>();

	    try {
	        sportBoardService.deleteBoard(id);
	        map.put("message", "글 삭제에 성공했습니다");
	    } catch (Exception e) {
	        map.put("message", "글 삭제에 실패했습니다");
	    }

	    return map;
	}
	
	//좋아요 수 조회
	@GetMapping("/{boardId}/likes")
	public int countLikes(@PathVariable String boardId) {
		return sportBoardService.countLikes(boardId);
	}
	
	//좋아요 생성
	@PostMapping("/{boardId}/likes")
	public ResponseEntity<Map<String, String>> insertLike(@PathVariable String boardId, @RequestBody LikeDto likeDto) {
	    Map<String, String> response = new HashMap<>();
	    likeDto.setBoardId(boardId);
	    int countLikes = sportBoardService.countLikes(boardId);
	    
	    if (countLikes > 0) {
	        response.put("message", "이미 좋아요를 눌렀습니다");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    boolean isCreated = sportBoardService.insertLike(likeDto);
	    
	    if (isCreated) {
	        response.put("message", "좋아요 추가 성공");
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } else {
	        response.put("message", "좋아요 추가 실패");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//좋아요 삭제
	@DeleteMapping("/{boardId}/likes")
	public ResponseEntity<Map<String, String>> deleteLike(@PathVariable String boardId, @RequestBody LikeDto likeDto) {
	    Map<String, String> response = new HashMap<>();
	    likeDto.setBoardId(boardId);
	    int countLikes = sportBoardService.countLikes(boardId);
	    
	    if (countLikes == 0) {
	        response.put("message", "좋아요를 누르지 않았습니다");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    int affectedRows = sportBoardService.deleteLike(likeDto);
	    
	    if (affectedRows > 0) {
	        response.put("message", "좋아요 삭제 성공");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put("message", "좋아요 삭제 실패");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<Map<String, String>> insertBookmark(@PathVariable String boardId, @RequestBody BookmarkDto bookmarkDto) {
	    Map<String, String> response = new HashMap<>();
	    bookmarkDto.setBoardId(boardId);
	    boolean isBookmarked = sportBoardService.isBookmarked(bookmarkDto);

	    if (isBookmarked) {
	        response.put("message", "이미 북마크를 추가했습니다");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    boolean isCreated = sportBoardService.insertBookmark(bookmarkDto);
	    
	    if (isCreated) {
	        response.put("message", "북마크 추가 성공");
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } else {
	        response.put("message", "북마크 추가 실패");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//북마크 삭제
	@DeleteMapping("/{boardId}/bookmark")
	public ResponseEntity<Map<String, String>> deleteBookmark(@PathVariable String boardId, @RequestBody BookmarkDto bookmarkDto) {
	    Map<String, String> response = new HashMap<>();
	    bookmarkDto.setBoardId(boardId);
	    boolean isBookmarked = sportBoardService.isBookmarked(bookmarkDto);

	    if (!isBookmarked) {
	        response.put("message", "북마크를 추가하지 않았습니다");
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    }

	    int affectedRows = sportBoardService.deleteBookmark(bookmarkDto);
	    
	    if (affectedRows > 0) {
	        response.put("message", "북마크 삭제 성공");
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        response.put("message", "북마크 삭제 실패");
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//해당 글의 해시태그 가져오기
	//입력 데이터:boardId / 출력 데이터:tagName or message(해시태그가 비어있거나 없는 게시글번호)
	@GetMapping("/hashtag")
	public ResponseEntity<List<Map<String, Object>>> findHashtagsByBoardId(@RequestBody SportBoardDto board) {
		List<SportBoardDto> tagNames = sportBoardService.findHashtagsByBoardId(board);
		List<Map<String, Object>> tagNameList = new ArrayList<>();
		for (SportBoardDto tagName : tagNames) {
			Map<String, Object> tagNameBoard = new HashMap<>();
			tagNameBoard.put("tagName", tagName.getTagName());
			tagNameList.add(tagNameBoard);
		}
		if (tagNameList.isEmpty()) {
			Map<String, Object> tagNameBoard = new HashMap<>();
			tagNameBoard.put("message", "해시태그가 없습니다");
			tagNameList.add(tagNameBoard);
			return new ResponseEntity<>(tagNameList, HttpStatus.OK);
		}
		return new ResponseEntity<>(tagNameList, HttpStatus.OK);
	}
	
}

package com.nutrimate.nutrimatebackend.controller.board.sport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.board.InfoBoardDto;
import com.nutrimate.nutrimatebackend.model.board.sport.BookmarkDto;
import com.nutrimate.nutrimatebackend.model.board.sport.LikeDto;
import com.nutrimate.nutrimatebackend.model.board.sport.SportBoardDto;
import com.nutrimate.nutrimatebackend.service.board.InfoBoardService;
import com.nutrimate.nutrimatebackend.service.board.sport.SportBoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/boards/sport")
@Log4j2
public class SportBoardController {

    private SportBoardService sportBoardService;
    private InfoBoardService infoBoardService;

    @Autowired
    public SportBoardController(SportBoardService sportBoardService,
            InfoBoardService infoBoardService) {
        this.sportBoardService = sportBoardService;
        this.infoBoardService = infoBoardService;
    }

    // 글 생성
    @PostMapping()
    public Map<String, String> createBoard(@RequestBody SportBoardDto board,
            HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        SportBoardDto createdBoard = sportBoardService.createBoard(board);
        if (createdBoard == null) {
            map.put("message", "글 작성에 실패했습니다");
        } else {
            map.put("message", "글 작성에 성공했습니다");
            map.put("boardId", String.valueOf(createdBoard.getBoardId())); // boardId 추가
        }
        return map;
    }

    // 글 조회(상세)
    @GetMapping("/{boardId}")
    public ResponseEntity<SportBoardDto> getBoardWithPrevAndNext(
            @PathVariable("boardId") int boardId, @RequestParam("userId") int userId) {
        SportBoardDto board = sportBoardService.getBoard(boardId, userId);

        InfoBoardDto dto = new InfoBoardDto();
        dto.setBoardId(boardId);
        dto.setBoardCategory("EXERCISE");
        Map<String, Integer> prevAndNext =
                infoBoardService.findPrevAndNextByBoardId(dto.getBoardId(), dto.getBoardCategory());
        if (prevAndNext != null) {
            board.setPrevBoardId(prevAndNext.get("PREV_BOARD_ID") == null ? null
                    : Integer.parseInt(String.valueOf(prevAndNext.get("PREV_BOARD_ID"))));
            board.setNextBoardId(prevAndNext.get("NEXT_BOARD_ID") == null ? null
                    : Integer.parseInt(String.valueOf(prevAndNext.get("NEXT_BOARD_ID"))));
        }

        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    // 글 목록 조회(전체)
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllBoards(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "searchUser", required = false) String searchUser,
            @RequestParam(name = "searchTitle", required = false) String searchTitle,
            @RequestParam(name = "searchContent", required = false) String searchContent,
            @RequestParam(name = "searchTag", required = false) String searchTag) {

        Map<String, Object> boards = sportBoardService.getBoards(pageNum, searchUser, searchTitle,
                searchContent, searchTag);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    // 글 수정
    @PutMapping("/{id}")
    public Map<String, String> updateBoard(@PathVariable("id") int id,
            @RequestBody SportBoardDto board, HttpServletRequest req) {
        board.setBoardId(id);

        Map<String, String> map = new HashMap<>();
        SportBoardDto updatedBoard = sportBoardService.updateBoard(board);

        if (updatedBoard == null) {
            map.put("message", "글 수정에 실패했습니다");
            return map;
        } else {
            map.put("message", "글 수정에 성공했습니다");
            return map;
        }
    }

    // 글 삭제
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

    // 좋아요 수 조회
    @GetMapping("/{boardId}/likes")
    public int countLikes(@PathVariable String boardId) {
        return sportBoardService.countLikes(boardId);
    }

    // 유저가 좋아요 누른지 확인 (완료)
    // 입력 데이터 : userId, boardId
    // 출력 데이터 : message, boardId, userId
    @PostMapping("/like/check")
    public ResponseEntity<Map<String, Object>> checkUserLike(@RequestBody LikeDto likeDto) {
        int checkUserLike = sportBoardService.checkUserLike(likeDto);
        Map<String, Object> jsonResponse = new HashMap<>();
        if (checkUserLike == 0) {
            jsonResponse.put("message", "좋아요를 안눌렀어요");
        } else {
            jsonResponse.put("message", "좋아요를 이미 눌렀어요");
        }
        jsonResponse.put("boardId", likeDto.getBoardId());
        jsonResponse.put("userId", likeDto.getUserId());
        return ResponseEntity.ok(jsonResponse);
    }

    // 좋아요 생성
    @PostMapping("/{boardId}/likes")
    public ResponseEntity<Map<String, String>> insertLike(@PathVariable String boardId,
            @RequestBody LikeDto likeDto) {
        Map<String, String> response = new HashMap<>();
        likeDto.setBoardId(boardId);
        boolean isLiked = sportBoardService.isLiked(likeDto);

        if (isLiked) {
            response.put("message", "이미 좋아요를 눌렀습니다");
            return new ResponseEntity<>(response, HttpStatus.OK); // 상태 코드를 OK로 변경
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

    // 좋아요 삭제
    @DeleteMapping("/{boardId}/likes")
    public ResponseEntity<Map<String, String>> deleteLike(@PathVariable String boardId,
            @RequestBody LikeDto likeDto) {
        Map<String, String> response = new HashMap<>();
        likeDto.setBoardId(boardId);
        int countLikes = sportBoardService.countLikes(boardId);
        log.info("boardId : " + boardId);
        log.info("countLikes : " + countLikes);

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
     * @DeleteMapping("/{boardId}/likes") public ResponseEntity<Map<String, String>>
     * deleteLike(@PathVariable String boardId, @RequestBody LikeDto likeDto) { Map<String, String>
     * response = new HashMap<>(); likeDto.setBoardId(boardId);
     * 
     * boolean isLikedByUser = sportBoardService.isLiked(likeDto);
     * 
     * if (!isLikedByUser) { response.put("message", "좋아요를 누르지 않았습니다"); return new
     * ResponseEntity<>(response, HttpStatus.BAD_REQUEST); }
     * 
     * int affectedRows = sportBoardService.deleteLike(likeDto);
     * 
     * if (affectedRows > 0) { response.put("message", "좋아요 삭제 성공"); return new
     * ResponseEntity<>(response, HttpStatus.OK); } else { response.put("message", "좋아요 삭제 실패");
     * return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); } }
     */

    /*
     * //이전글 조회
     * 
     * @GetMapping("/{id}/prev") public ResponseEntity<BoardDto> getPrevBoard(@PathVariable("id")
     * int id) { BoardDto prevBoard = boardService.getPrevBoard(id); if (prevBoard != null) { return
     * new ResponseEntity<>(prevBoard, HttpStatus.OK); } else { return new
     * ResponseEntity<>(HttpStatus.NO_CONTENT); } }
     * 
     * //다음글 조회
     * 
     * @GetMapping("/{id}/next") public ResponseEntity<BoardDto> getNextBoard(@PathVariable("id")
     * int id) { BoardDto nextBoard = boardService.getNextBoard(id); if (nextBoard != null) { return
     * new ResponseEntity<>(nextBoard, HttpStatus.OK); } else { return new
     * ResponseEntity<>(HttpStatus.NO_CONTENT); } }
     */

    // 북마크 생성
    @PostMapping("/{boardId}/bookmark")
    public ResponseEntity<Map<String, String>> insertBookmark(@PathVariable String boardId,
            @RequestBody BookmarkDto bookmarkDto) {
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

    // 북마크 삭제
    @DeleteMapping("/{boardId}/bookmark")
    public ResponseEntity<Map<String, String>> deleteBookmark(@PathVariable String boardId,
            @RequestBody BookmarkDto bookmarkDto) {
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

    // 해당 글의 해시태그 가져오기
    // 입력 데이터:boardId / 출력 데이터:tagName or message(해시태그가 비어있거나 없는 게시글번호)
    @GetMapping("/{boardId}/hashtag")
    public ResponseEntity<List<Map<String, Object>>> findHashtagsByBoardId(
            @PathVariable String boardId) {
        List<SportBoardDto> tagNames = sportBoardService.findHashtagsByBoardId(boardId);
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

    // 해시태그로 글 검색 (완료)
    // 입력 데이터 : tagName
    // 출력 데이터 : BoardList or message(해당 해시태그로 검색된 글이 없음)
    @GetMapping("/hashtag/search")
    public ResponseEntity<List<Map<String, Object>>> findBoardsByTagName(
            @RequestBody SportBoardDto board) {
        List<SportBoardDto> findBoardsByTagNames = sportBoardService.findBoardsByTagName(board);
        int boardId = board.getBoardId();
        List<Map<String, Object>> BoardsByTagNameList = new ArrayList<>();
        for (SportBoardDto findBoardsByTagName : findBoardsByTagNames) {
            Map<String, Object> BoardList = new HashMap<>();
            BoardList.put("BoardList", findBoardsByTagName.getBoardId());
            BoardsByTagNameList.add(BoardList);
        }
        if (BoardsByTagNameList.isEmpty()) {
            Map<String, Object> tagNameBoard = new HashMap<>();
            tagNameBoard.put("message", "해당 해시태그로 검색된 글이 없어요");
            BoardsByTagNameList.add(tagNameBoard);
            return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
        }
        return new ResponseEntity<>(BoardsByTagNameList, HttpStatus.OK);
    }

}

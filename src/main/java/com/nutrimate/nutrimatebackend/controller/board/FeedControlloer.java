package com.nutrimate.nutrimatebackend.controller.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.board.FeedDto;
import com.nutrimate.nutrimatebackend.service.board.FeedService;

@RestController
@RequestMapping("/api/board/Feed")
public class FeedControlloer {

  @Autowired
  private FeedService feedService;

  /** 피드 **/
  // 피드 글목록 가져오기 (완료)
  // 입력 데이터 : nowPage(디폴트1), receivePage(디폴트10)
  // 출력 데이터 : boardId, thumbnail, nowPage, receivePage, totalPages, totalRecordCount
  @GetMapping("/FeedList")
  public ResponseEntity<List<Map<String, Object>>> findFeedList(
      @RequestParam(defaultValue = "1") int nowPage,
      @RequestParam(defaultValue = "10") int receivePage) {

    int totalRecordCount = feedService.findFeedtotalRecordCount();

    // 페이징 계산
    int totalPages = (int) Math.ceil((double) totalRecordCount / receivePage);
    int startRow = (nowPage - 1) * receivePage + 1;
    int endRow = nowPage * receivePage;

    // 피드 목록 가져오기
    List<FeedDto> FeedList = feedService.findFeedList(startRow, endRow);

    // 각 피드에 페이징 정보 추가
    List<Map<String, Object>> simplifiedFeedList = new ArrayList<>();
    for (FeedDto feed : FeedList) {
      Map<String, Object> simplifiedFeed = new HashMap<>();
      simplifiedFeed.put("boardId", feed.getBoardId());
      simplifiedFeed.put("thumbnail", feed.getBoardThumbnail());
      simplifiedFeed.put("nowPage", nowPage);
      simplifiedFeed.put("receivePage", receivePage);
      simplifiedFeed.put("totalPages", totalPages);
      simplifiedFeed.put("totalRecordCount", totalRecordCount);
      simplifiedFeedList.add(simplifiedFeed);
    }

    return new ResponseEntity<>(simplifiedFeedList, HttpStatus.OK);
  }

  // 피드 상세보기 정보 가져오기 (해시태그 입력 후 재확인 필요)
  // 댓글 수는 제외하고 조회(상세보기 화면이니까:해당 글번호의 모든 댓글 뿌리니까)
  // 좋아요, 북마크 누른 여부는 제외하고 조회(따로 확인)
  // 입력 데이터 : boardId
  // 출력 데이터 : boardId, userNick, userProfile, createdDate, boardThumbnail, boardViewCount,
  // LIKE_COUNT, HASHTAG
  @GetMapping("/findFeedDetail")
  public ResponseEntity<List<Map<String, Object>>> findFeedDetail(@RequestBody FeedDto feedDto) {
    List<FeedDto> FeedList = feedService.findFeedDetail(feedDto);

    List<Map<String, Object>> detailfiedFeedList = new ArrayList<>();
    for (FeedDto feed : FeedList) {
      Map<String, Object> detailfiedFeed = new HashMap<>();
      detailfiedFeed.put("boardId", feed.getBoardId());
      detailfiedFeed.put("userNick", feed.getBoardThumbnail());
      detailfiedFeed.put("userProfile", feed.getUserProfile());
      detailfiedFeed.put("createdDate", feed.getCreatedDate());
      detailfiedFeed.put("boardThumbnail", feed.getBoardThumbnail());
      detailfiedFeed.put("boardViewCount", feed.getBoardViewCount()); // 조회수
      detailfiedFeed.put("LIKE_COUNT", feed.getLikeCount()); // 좋아요 수
      detailfiedFeed.put("HASHTAG", feed.getHashtag()); // 해시태그
      detailfiedFeedList.add(detailfiedFeed);
    }

    return new ResponseEntity<>(detailfiedFeedList, HttpStatus.OK);
  }

  // 피드의 조회수를 +1하기 (완료)
  // 입력 데이터 : boardId
  // 출력 데이터 : message, boardViewCount
  @PutMapping("/updateincreaseViewCount")
  public ResponseEntity<Map<String, Object>> updateincreaseViewCount(@RequestBody FeedDto feedDto) {
    feedService.updateincreaseViewCount(feedDto);
    int boardViewCount = feedDto.getBoardViewCount();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "boardViewCount Update successfully");
    jsonResponse.put("boardViewCount", boardViewCount);
    return ResponseEntity.ok(jsonResponse);
  }

  // 피드 작성
  // 입력 데이터 : boardId, userId, boardTitle, boardContent, boardThumbnail, tagName
  // 썸네일 입력 예시 : "boardThumbnail" : ""
  // 해시태그 입력 예시 : "tagName" : [ "태그1", "태그2", "태그3" ]
  // 출력 데이터 : message, boardId, tagId
  @PostMapping("/insertFeed")
  public ResponseEntity<Map<String, Object>> insertFeed(@RequestBody FeedDto feedDto) {

    // 피드 작성
    feedService.insertFeed(feedDto);

    int boardId = feedDto.getBoardId();
    int tagId = feedDto.getTagId();

    /*
     * // 해시태그 작성 List<String> hashtags = feedDto.getHashtag(); for (String tagName : hashtags) {
     * feedService.insertHashtag(tagId, tagName); }
     */

    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Feed inserted successfully");
    jsonResponse.put("boardId", boardId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 피드 수정
  @PutMapping("/updateFeed")
  public void updateFeed(@RequestBody FeedDto feedDto) {
    feedService.updateFeed(feedDto);
  }

  // 피드 삭제
  @DeleteMapping("/deleteFeed")
  public void deleteFeed(@RequestBody FeedDto feedDto) {
    feedService.deleteFeed(feedDto);
  }


  /** 좋아요 **/
  // 유저가 좋아요 누른지 확인
  @GetMapping("/checkUserLike")
  public int checkUserLike(@RequestBody FeedDto feedDto) {
    return feedService.checkUserLike(feedDto);
  }

  // 해당 글의 좋아요 수를 가져오기
  @GetMapping("/findLikeCount")
  public int findLikeCount(@RequestBody FeedDto feedDto) {
    return feedService.findLikeCount(feedDto);
  }

  // 좋아요 추가
  @PostMapping("/insertLike")
  public void insertLike(@RequestBody FeedDto feedDto) {
    feedService.insertLike(feedDto);
  }

  // 좋아요 해제
  @DeleteMapping("/deleteLike")
  public void deleteLike(@RequestBody FeedDto feedDto) {
    feedService.deleteLike(feedDto);
  }


  /** 북마크 **/
  // 유저가 북마크 누른지 확인
  @GetMapping("/checkUserBookmark/")
  public int checkUserBookmark(@RequestBody FeedDto feedDto) {
    return feedService.checkUserBookmark(feedDto);
  }

  // 북마크 추가
  @PostMapping("/insrtBookmark")
  public void insrtBookmark(@RequestBody FeedDto feedDto) {
    feedService.insrtBookmark(feedDto);
  }

  // 북마크 해제
  @DeleteMapping("/deleteBookmark")
  public void deleteBookmark(@RequestBody FeedDto feedDto) {
    feedService.deleteBookmark(feedDto);
  }


  /** 해시태그 **/
  // 해당 글의 해시태그 가져오기
  @GetMapping("/findHashtagsByBoardId")
  public List<FeedDto> findHashtagsByBoardId(@RequestBody FeedDto feedDto) {
    return feedService.findHashtagsByBoardId(feedDto);
  }

  // 해시태그로 글 검색
  @GetMapping("/findBoardsByTagName")
  public List<FeedDto> findBoardsByTagName(@RequestBody FeedDto feedDto) {
    return feedService.findBoardsByTagName(feedDto);
  }


  /** 신고 **/
  // 글 신고
  @PostMapping("/reportBoard")
  public ResponseEntity<String> insertBoardReport(@RequestBody FeedDto feedDto) {
    feedService.insertReportBo(feedDto);
    feedService.insertBoardReport(feedDto);

    return ResponseEntity.ok("Board reported successfully.");
  }

  // 댓글/대댓글 신고
  @PostMapping("/reportComment")
  public ResponseEntity<String> insertCommentReport(@RequestBody FeedDto feedDto) {
    feedService.insertReportCo(feedDto);
    feedService.insertCommentReport(feedDto);

    return ResponseEntity.ok("Comment reported successfully.");
  }

  // 글신고 취소하기
  @PostMapping("/deleteBoardReport")
  public ResponseEntity<String> deleteBoardReport(@RequestBody FeedDto feedDto) {
    int boardId = feedDto.getBoardId();
    int userId = feedDto.getUserId();

    feedService.deleteBoardReport(feedDto);

    return ResponseEntity.ok("Board report deleted successfully.");
  }

  // 댓글신고 취소하기
  @PostMapping("/deleteCommentReport")
  public ResponseEntity<String> deleteCommentReport(@RequestBody FeedDto feedDto) {
    int commentId = feedDto.getCmtId();
    int userId = feedDto.getUserId();

    feedService.deleteCommentReport(feedDto);

    return ResponseEntity.ok("Comment report deleted successfully.");
  }

  // 신고된 글 보기 (관리자 페이지)
  @GetMapping("/findReportedBoards")
  public ResponseEntity<List<FeedDto>> findReportedBoards() {
    List<FeedDto> reportedBoards = feedService.findReportedBoards();
    return ResponseEntity.ok(reportedBoards);
  }

  // 신고된 댓글 보기 (관리자 페이지)
  @GetMapping("/findReportedComments")
  public ResponseEntity<List<FeedDto>> findReportedComments() {
    List<FeedDto> reportedComments = feedService.findReportedComments();
    return ResponseEntity.ok(reportedComments);
  }

}

package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.member.FeedDto;
import com.nutrimate.nutrimatebackend.service.test.FeedService;

@RestController
@RequestMapping("/api/board/Feed")
public class FeedControlloer {

  @Autowired
  private FeedService feedService;

  /** 피드 **/
  // 피드 글목록 가져오기
  @GetMapping("/FeedList")
  public List<FeedDto> findFeedList() {
    return feedService.findFeedList();
  }

  // 피드 상세보기 정보 가져오기
  @GetMapping("/findFeedDetail")
  public FeedDto findFeedDetail(@RequestBody FeedDto feedDto) {
    return feedService.findFeedDetail(feedDto);
  }

  // 피드의 조회수를 +1하기
  @PutMapping("/updateincreaseViewCount")
  public void updateincreaseViewCount(@RequestBody FeedDto feedDto) {
    feedService.updateincreaseViewCount(feedDto);
  }

  // 피드 작성
  @PostMapping("/insertFeed")
  public void insertFeed(@RequestBody FeedDto feedDto) {
    feedService.insertFeed(feedDto);
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

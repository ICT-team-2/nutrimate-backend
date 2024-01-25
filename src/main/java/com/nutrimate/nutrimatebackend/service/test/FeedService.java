package com.nutrimate.nutrimatebackend.service.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nutrimate.nutrimatebackend.mapper.test.FeedMapper;
import com.nutrimate.nutrimatebackend.model.member.FeedDto;

@Service
public class FeedService {

  @Autowired
  private FeedMapper feedMapper;

  /** 피드 기능 **/
  // 피드 글목록 가져오기
  public List<FeedDto> findFeedList() {
    return feedMapper.findFeedList();
  }

  // 피드의 상세보기 정보를 가져오기
  public FeedDto findFeedDetail(FeedDto feedDto) {
    return feedMapper.findFeedDetail(feedDto);
  }

  // 피드의 조회수를 +1하기
  public void updateincreaseViewCount(FeedDto feedDto) {
    feedMapper.updateincreaseViewCount(feedDto);
  }

  // 피드 작성
  @Transactional
  public void insertFeed(FeedDto feedDto) {
    feedMapper.insertFeed(feedDto);
    feedMapper.insertTag(feedDto);
    feedMapper.insertHashtag(feedDto);
  }

  // 피드 수정
  @Transactional
  public void updateFeed(FeedDto feedDto) {
    feedMapper.updateFeed(feedDto);
    feedMapper.updateHashtag(feedDto);
  }

  // 피드 삭제
  public void deleteFeed(FeedDto feedDto) {
    feedMapper.deleteFeed(feedDto);
  }


  /** 좋아요 **/
  // 유저가 좋아요 누른지 확인
  public int checkUserLike(FeedDto feedDto) {
    return feedMapper.checkUserLike(feedDto);
  }

  // 해당 글의 좋아요 수를 가져오기
  public int findLikeCount(FeedDto feedDto) {
    return feedMapper.findLikeCount(feedDto);
  }

  // 좋아요 추가
  public void insertLike(FeedDto feedDto) {
    feedMapper.insertLike(feedDto);
  }

  // 좋아요 해제
  public void deleteLike(FeedDto feedDto) {
    feedMapper.deleteLike(feedDto);
  }


  /** 북마크 **/
  // 유저가 북마크 누른지 확인
  public int checkUserBookmark(FeedDto feedDto) {
    return feedMapper.checkUserBookmark(feedDto);
  }

  // 북마크 추가
  public void insrtBookmark(FeedDto feedDto) {
    feedMapper.insrtBookmark(feedDto);
  }

  // 북마크 해제
  public void deleteBookmark(FeedDto feedDto) {
    feedMapper.deleteBookmark(feedDto);
  }


  /** 해시태그 **/
  // 해당 글의 해시태그 가져오기
  public List<FeedDto> findHashtagsByBoardId(FeedDto feedDto) {
    return feedMapper.findHashtagsByBoardId(feedDto);
  }

  // 해시태그로 글 검색
  public List<FeedDto> findBoardsByTagName(FeedDto feedDto) {
    return feedMapper.findBoardsByTagName(feedDto);
  }


  /** 신고 **/
  // 글 신고
  public void insertReportBo(FeedDto feedDto) {
    feedMapper.insertReportBo(feedDto);

  }

  public void insertBoardReport(FeedDto feedDto) {
    feedMapper.insertBoardReport(feedDto);

  }

  // 댓글/대댓글 신고
  public void insertReportCo(FeedDto feedDto) {
    feedMapper.insertReportCo(feedDto);
  }

  public void insertCommentReport(FeedDto feedDto) {
    feedMapper.insertCommentReport(feedDto);
  }

  // 글신고 취소하기
  public void deleteBoardReport(FeedDto feedDto) {
    feedMapper.deleteBoardReport(feedDto);
  }

  // 댓글신고 취소하기
  public void deleteCommentReport(FeedDto feedDto) {
    feedMapper.deleteCommentReport(feedDto);
  }

  // 신고된 글 보기 (관리자 페이지)
  public List<FeedDto> findReportedBoards() {
    return feedMapper.findReportedBoards();
  }

  // 신고된 댓글 보기 (관리자 페이지)
  public List<FeedDto> findReportedComments() {
    return feedMapper.findReportedComments();
  }



}

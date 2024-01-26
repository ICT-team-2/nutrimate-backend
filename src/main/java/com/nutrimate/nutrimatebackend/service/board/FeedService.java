package com.nutrimate.nutrimatebackend.service.board;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nutrimate.nutrimatebackend.mapper.board.FeedMapper;
import com.nutrimate.nutrimatebackend.model.board.FeedDto;

@Service
public class FeedService {

  @Autowired
  private FeedMapper feedMapper;

  /** 피드 기능 **/
  public List<FeedDto> findFeedList(@Param("startRow") int startRow, @Param("endRow") int endRow) {
    return feedMapper.findFeedList(startRow, endRow);
  }

  public int findFeedtotalRecordCount() {
    return feedMapper.findFeedtotalRecordCount();
  }

  // 피드의 상세보기 정보를 가져오기
  public List<FeedDto> findFeedDetail(FeedDto feedDto) {
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
    int boardId = feedDto.getBoardId();

    // 해시태그 작성
    List<String> hashtags = feedDto.getHashtag();
    for (String tag : hashtags) {
      int checkTagId = feedMapper.checkTagId(tag);
      if (checkTagId == 0) {
        feedMapper.insertTag(tag);
      } else {
        System.out.println("중복키 값이 있어요");
      }
      feedDto.setBoardId(boardId);
      feedDto.setTagName(tag);
      feedMapper.insertHashtag(feedDto);
    }
  }

  // 피드 수정
  @Transactional
  public void updateFeed(FeedDto feedDto) {
    feedMapper.updateFeed(feedDto);
    feedMapper.updateHashtag(feedDto);
    int boardId = feedDto.getBoardId();

    // 해당 글의 해시태그 삭제 후 작성
    List<String> hashtags = feedDto.getHashtag();
    for (String tag : hashtags) {
      int checkTagId = feedMapper.checkTagId(tag);
      if (checkTagId == 0) {
        feedMapper.insertTag(tag);
      } else {
        System.out.println("중복키 값이 있어요");
      }
      feedDto.setBoardId(boardId);
      feedDto.setTagName(tag);
      feedMapper.insertHashtag(feedDto);
    }
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

  // 북마크된 글 목록 가져오기 (마이페이지)
  public List<FeedDto> findBookmarkFeedByUserId(FeedDto feedDto) {
    return feedMapper.findBookmarkFeedByUserId(feedDto);
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

}

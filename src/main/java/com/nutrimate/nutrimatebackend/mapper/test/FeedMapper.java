package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.member.FeedDto;

@Mapper
public interface FeedMapper {

  /** 피드 기능 **/
  // 피드 글목록 가져오기
  List<FeedDto> findFeedList();

  // 피드의 상세보기 정보를 가져오는 쿼리문:
  FeedDto findFeedDetail(FeedDto feedDto);

  // 피드의 조회수를 +1하기
  void updateincreaseViewCount(FeedDto feedDto);

  // 피드 작성
  void insertFeed(FeedDto feedDto);

  void insertTag(FeedDto feedDto);

  void insertHashtag(FeedDto feedDto);

  // 피드 수정
  void updateFeed(FeedDto feedDto);

  void updateHashtag(FeedDto feedDto);

  // 피드 삭제
  void deleteFeed(FeedDto feedDto);

}

package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/board")
public class FeedControlloer {

  @Autowired
  private FeedService feedService;

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

}

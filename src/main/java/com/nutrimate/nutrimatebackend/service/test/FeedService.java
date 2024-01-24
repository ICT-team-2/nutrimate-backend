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

  public List<FeedDto> findFeedList() {
    return feedMapper.findFeedList();
  }

  public FeedDto findFeedDetail(FeedDto feedDto) {
    return feedMapper.findFeedDetail(feedDto);
  }

  public void updateincreaseViewCount(FeedDto feedDto) {
    feedMapper.updateincreaseViewCount(feedDto);
  }

  @Transactional
  public void insertFeed(FeedDto feedDto) {
    feedMapper.insertFeed(feedDto);
    feedMapper.insertTag(feedDto);
    feedMapper.insertHashtag(feedDto);
  }

  @Transactional
  public void updateFeed(FeedDto feedDto) {
    feedMapper.updateFeed(feedDto);
    feedMapper.updateHashtag(feedDto);
  }

  public void deleteFeed(FeedDto feedDto) {
    feedMapper.deleteFeed(feedDto);
  }


}

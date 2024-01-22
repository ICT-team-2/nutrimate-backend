package com.nutrimate.nutrimatebackend.service.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.test.FollowMapper;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;

@Service
public class FollowService {

  @Autowired
  private FollowMapper followMapper;

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  public List<FollowDto> getRecommendedFollowers(FollowDto followDto) {
    return followMapper.getRecommendedFollowers();
  }

  // 내가 상대를 팔로우 하는 쿼리문
  public int followUser(FollowDto followDto) {
    return followMapper.followUser(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  public int getFollowingCount(FollowDto userId) {
    return followMapper.getFollowingCount(userId);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  public int getFollowerCount(FollowDto userId) {
    return followMapper.getFollowerCount(userId);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  public List<FollowDto> getFollowingList(FollowDto userId) {
    return followMapper.getFollowingList(userId);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  public List<FollowDto> getFollowerList(FollowDto userId) {
    return followMapper.getFollowerList(userId);
  }

  // 팔로우 유무 확인 (0일시 안누름)
  public int checkFollowStatus(FollowDto followDto) {
    return followMapper.checkFollowStatus(followDto);
  }

  // 내가 등록한 사람 삭제. 팔로우 취소
  public int unfollowUser(FollowDto followId) {
    return followMapper.unfollowUser(followId);
  }

}

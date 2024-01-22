package com.nutrimate.nutrimatebackend.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.test.FollowMapper;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;

@Service
public class FollowService {

  @Autowired
  private FollowMapper followMapper;

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  public int getRecommendedFollowers(FollowDto followDto) {
    return followMapper.getRecommendedFollowers(followDto);
  }

  // 내가 상대를 팔로우 하는 쿼리문
  public int followUser(FollowDto followDto) {
    return followMapper.followUser(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  public int getFollowingCount(FollowDto followDto) {
    return followMapper.getFollowingCount(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  public int getFollowerCount(FollowDto followDto) {
    return followMapper.getFollowerCount(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  public int getFollowingList(FollowDto followDto) {
    return followMapper.getFollowingList(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  public int getFollowerList(FollowDto followDto) {
    return followMapper.getFollowerList(followDto);
  }

  // 팔로우 유무 확인 (0일시 안누름)
  public int checkFollowStatus(FollowDto followDto) {
    return followMapper.checkFollowStatus(followDto);
  }

  // 내가 등록한 사람 삭제. 팔로우 취소
  public int unfollowUser(FollowDto followDto) {
    return followMapper.unfollowUser(followDto);
  }

}

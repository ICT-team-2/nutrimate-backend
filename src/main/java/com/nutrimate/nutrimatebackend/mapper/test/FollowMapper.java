package com.nutrimate.nutrimatebackend.mapper.test;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;

@Mapper
public interface FollowMapper {

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  int getRecommendedFollowers(FollowDto todoDto);

  // 내가 상대를 팔로우 하는 쿼리문
  int followUser(FollowDto followDto);

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  int getFollowingCount(FollowDto followDto);

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  int getFollowerCount(FollowDto followDto);

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  int getFollowingList(FollowDto followDto);

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  int getFollowerList(FollowDto followDto);

  // 팔로우 유무 확인 (0일시 안누름)
  int checkFollowStatus(FollowDto followDto);

  // 내가 등록한 사람 삭제. 팔로우 취소
  int unfollowUser(FollowDto followDto);

}

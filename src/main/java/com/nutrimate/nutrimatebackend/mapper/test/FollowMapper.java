package com.nutrimate.nutrimatebackend.mapper.test;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;

@Mapper
public interface FollowMapper {

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  List<FollowDto> findRecommendedFollowersList();

  // 내가 상대를 팔로우 하는 쿼리문 (내가 팔로워가 된다)
  void insertFollowUser(FollowDto followDto);

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  int findFollowingCount(FollowDto followDto);

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  int findFollowerCount(FollowDto followDto);

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  List<FollowDto> findFollowingList(FollowDto followDto);

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  List<FollowDto> findFollowerList(FollowDto followDto);

  // 팔로우 유무 확인 (0일시 안누름)
  int checkFollowStatus(FollowDto followDto);

  // 내가 등록한 사람 삭제. 팔로우 취소
  int deletefollowUser(FollowDto followDto);

}

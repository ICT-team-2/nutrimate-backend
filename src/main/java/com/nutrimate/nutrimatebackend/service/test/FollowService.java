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
  public List<FollowDto> findRecommendedFollowersList() {
    return followMapper.findRecommendedFollowersList();
  }

  // 내가 상대를 팔로우 하는 쿼리문 (내가 팔로워가 된다)
  public void insertFollowUser(FollowDto followDto) {
    followMapper.insertFollowUser(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  public int findFollowingCount(FollowDto followDto) {
    return followMapper.findFollowingCount(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  public int findFollowerCount(FollowDto followDto) {
    return followMapper.findFollowerCount(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  public List<FollowDto> findFollowingList(FollowDto followDto) {
    return followMapper.findFollowingList(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  public List<FollowDto> findFollowerList(FollowDto followDto) {
    return followMapper.findFollowerList(followDto);
  }

  // 팔로우 유무 확인 (0일시 안누름)
  public int checkFollowStatus(FollowDto followDto) {
    return followMapper.checkFollowStatus(followDto);
  }

  // 내가 등록한 사람 삭제. 팔로우 취소
  public int deletefollowUser(FollowDto followDto) {
    return followMapper.deletefollowUser(followDto);
  }

}

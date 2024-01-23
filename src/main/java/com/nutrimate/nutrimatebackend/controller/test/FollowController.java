package com.nutrimate.nutrimatebackend.controller.test;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;
import com.nutrimate.nutrimatebackend.service.test.FollowService;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

  /**
   * POST - CREATE GET - SELECT PUT - UPDATE DELETE - DELETE
   */

  @Autowired
  private FollowService followservice;

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원정보 가져오기) (완료)
  // 출력 데이터 : user_id,user_profile,user_nickname,user_intro
  @GetMapping("/Recommend")
  public List<FollowDto> getRecommendedFollowers() {
    return followservice.getRecommendedFollowers();
  }

  // 내가 상대를 팔로우 하는 쿼리문 (내가 팔로워가 된다) (완료)
  // 입력 데이터 : followerId(내userId), followeeId(상대의userId)
  @PostMapping("/FollowUser")
  public int followUser(@RequestBody FollowDto followDto) {
    return followservice.followUser(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기 (완료)
  // 입력 데이터 : userId
  @GetMapping("/FollowingCount")
  public int getFollowingCount(@RequestBody FollowDto userId) {
    return followservice.getFollowingCount(userId);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기 (완료)
  // 입력 데이터 : userId
  @GetMapping("/FollowerCount")
  public int getFollowerCount(@RequestBody FollowDto userId) {
    return followservice.getFollowerCount(userId);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기 (완료)
  // 입력 데이터 : userId
  @GetMapping("/FollowingList")
  public List<FollowDto> getFollowingList(@RequestBody FollowDto userId) {
    return followservice.getFollowingList(userId);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기 (완료)
  // 입력 데이터 : userId
  @GetMapping("/FollowerList")
  public List<FollowDto> getFollowerList(@RequestBody FollowDto userId) {
    return followservice.getFollowerList(userId);
  }

  // 팔로우 유무 확인 (0일시 안누름) (완료)
  // 입력 데이터 : userId,followeeId
  @GetMapping("/FollowStatus")
  public int checkFollowStatus(@RequestBody FollowDto followDto) {
    return followservice.checkFollowStatus(followDto);
  }

  // 내가 등록한 사람 삭제. 팔로우 취소 (완료)
  // 입력 데이터 : followId
  @DeleteMapping("/Unfollow")
  public int unfollowUser(@RequestBody FollowDto followId) {
    return followservice.unfollowUser(followId);
  }

}

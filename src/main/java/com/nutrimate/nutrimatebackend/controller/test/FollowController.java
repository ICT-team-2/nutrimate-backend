package com.nutrimate.nutrimatebackend.controller.test;

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

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원 가져오기)
  @GetMapping("/Recommend")
  public int getRecommendedFollowers(@RequestBody FollowDto followDto) {
    return followservice.getRecommendedFollowers(followDto);
  }

  // 내가 상대를 팔로우 하는 쿼리문
  @PostMapping("/followUser")
  public int followUser(@RequestBody FollowDto followDto) {
    return followservice.followUser(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기
  @GetMapping("/FollowingCount")
  public int getFollowingCount(@RequestBody FollowDto followDto) {
    return followservice.getFollowingCount(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기
  @GetMapping("/FollowerCount")
  public int getFollowerCount(@RequestBody FollowDto followDto) {
    return followservice.getFollowerCount(followDto);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기
  @GetMapping("/FollowingList")
  public int getFollowingList(@RequestBody FollowDto followDto) {
    return followservice.getFollowingList(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기
  @GetMapping("/FollowerList")
  public int getFollowerList(@RequestBody FollowDto followDto) {
    return followservice.getFollowerList(followDto);
  }

  // 팔로우 유무 확인 (0일시 안누름)
  @GetMapping("/FollowStatus")
  public int checkFollowStatus(@RequestBody FollowDto followDto) {
    return followservice.checkFollowStatus(followDto);
  }

  // 내가 등록한 사람 삭제. 팔로우 취소
  @DeleteMapping("/unfollow")
  public int unfollowUser(@RequestBody FollowDto followDto) {
    return followservice.unfollowUser(followDto);
  }

}

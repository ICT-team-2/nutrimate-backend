package com.nutrimate.nutrimatebackend.controller.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.member.FollowDto;
import com.nutrimate.nutrimatebackend.service.test.FollowService;

@RestController
@RequestMapping("/follow")
public class FollowController {

  @Autowired
  private FollowService followservice;

  // 회원을 위한 추천 팔로우(랜덤으로 5명의 회원정보 가져오기) (완료)
  // 입력 데이터 : 없음
  // 출력 데이터 : user_id,user_profile,user_nickname,user_intro... Dto정보 반환
  @GetMapping("/recommend")
  public List<FollowDto> findRecommendedFollowersList() {
    return followservice.findRecommendedFollowersList();
  }

  // 내가 상대를 팔로우 하는 쿼리문 (내가 팔로워가 된다) (완료)
  // 입력 데이터 : followerId(내userId), followeeId(상대의userId)
  // 출력 데이터 : message, followerId, followeeId, recordId(follow_id)
  @PostMapping("/follow")
  public ResponseEntity<Map<String, Object>> insertFollowUser(@RequestBody FollowDto followDto) {
    followservice.insertFollowUser(followDto);
    int followerId = followDto.getFollowerId();
    int followeeId = followDto.getFolloweeId();
    int recordId = followDto.getRecordId();
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "Follow successfully");
    jsonResponse.put("followerId", followerId);
    jsonResponse.put("followeeId", followeeId);
    jsonResponse.put("recordId", recordId);
    return ResponseEntity.ok(jsonResponse);
  }

  // 내 팔로잉(내가 등록한 사람) 수를 가져오기 (완료)
  // 입력 데이터 : userId
  // 출력 데이터 : message, followingCount
  @GetMapping("/followingCount")
  public ResponseEntity<Map<String, Object>> findFollowingCount(@RequestBody FollowDto followDto) {
    int followingCount = followservice.findFollowingCount(followDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "FollowingCount successfully");
    jsonResponse.put("followingCount", followingCount);
    return ResponseEntity.ok(jsonResponse);
  }

  // 내 팔로워(나를 등록한 사람) 수를 가져오기 (완료)
  // 입력 데이터 : userId
  // 출력 데이터 : message, followingCount
  @GetMapping("/followerCount")
  public ResponseEntity<Map<String, Object>> findFollowerCount(@RequestBody FollowDto followDto) {
    int followerCount = followservice.findFollowerCount(followDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", "followerCount successfully");
    jsonResponse.put("followerCount", followerCount);
    return ResponseEntity.ok(jsonResponse);
  }

  // 내 팔로잉(내가 등록한 사람) 목록을 가져오기 (완료)
  // 입력 데이터 : userId
  // 출력 데이터 : user_id,user_profile,user_nickname,user_intro... Dto정보 반환
  @GetMapping("/followingList")
  public List<FollowDto> findFollowingList(@RequestBody FollowDto followDto) {
    return followservice.findFollowingList(followDto);
  }

  // 내 팔로워(나를 등록한 사람) 목록을 가져오기 (완료)
  // 입력 데이터 : userId
  // 출력 데이터 : user_id,user_profile,user_nickname,user_intro... Dto정보 반환
  @GetMapping("/followerList")
  public List<FollowDto> findFollowerList(@RequestBody FollowDto followDto) {
    return followservice.findFollowerList(followDto);
  }

  // 팔로우한 유저인지 확인 (완료)
  // 입력 데이터 : userId,followeeId
  // 출력 데이터 : message, followStatus
  @GetMapping("/checkFollow")
  public ResponseEntity<Map<String, Object>> checkFollowStatus(@RequestBody FollowDto followDto) {
    Integer followStatus = followservice.checkFollowStatus(followDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", (followStatus >= 1) ? "팔로우 중인 유저에요" : "팔로우가 아니에요");
    jsonResponse.put("followStatus", followStatus);
    return ResponseEntity.ok(jsonResponse);
  }

  // 팔로우 취소(내가 등록한 사람 삭제)
  // 입력 데이터 : userId,followeeId
  // 출력 데이터 : message
  @DeleteMapping("/Unfollow")
  public ResponseEntity<Map<String, Object>> deletefollowUser(@RequestBody FollowDto followDto) {
    Integer Unfollow = followservice.deletefollowUser(followDto);
    Map<String, Object> jsonResponse = new HashMap<>();
    jsonResponse.put("message", (Unfollow >= 1) ? "팔로우를 해제했어요" : "팔로우 상태가 아니에요");
    jsonResponse.put("Unfollow", Unfollow);
    return ResponseEntity.ok(jsonResponse);
  }

}

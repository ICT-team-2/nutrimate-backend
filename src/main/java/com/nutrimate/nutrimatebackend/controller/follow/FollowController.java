package com.nutrimate.nutrimatebackend.controller.follow;

import com.nutrimate.nutrimatebackend.model.follow.FollowDto;
import com.nutrimate.nutrimatebackend.service.follow.FollowService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
@Log4j2
public class FollowController {
	
	@Autowired
	private FollowService followservice;
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Map> handleSQLException(SQLException e) {
		
		log.info("SQLException Occured", e);
		Map<String, String> response = new HashMap<>();
		response.put("status", "400");
		response.put("error", "SQLException");
//        response.put("message", e.getMessage()); // 상용화 시 이건 숨기는 게 좋음
		return ResponseEntity.badRequest().body(response);
	}
	
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
	public ResponseEntity<Map<String, Object>> insertFollowUser(@RequestBody FollowDto followDto) throws SQLException {
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
	@GetMapping("/following/count")
	public ResponseEntity<Map<String, Object>> findFollowingCount(FollowDto followDto) {
		int followingCount = followservice.findFollowingCount(followDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("message", "FollowingCount successfully");
		jsonResponse.put("followingCount", followingCount);
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 내 팔로워(나를 등록한 사람) 수를 가져오기 (완료)
	// 입력 데이터 : userId
	// 출력 데이터 : message, followingCount
	@GetMapping("/follower/count")
	public ResponseEntity<Map<String, Object>> findFollowerCount(FollowDto followDto) {
		int followerCount = followservice.findFollowerCount(followDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("message", "followerCount successfully");
		jsonResponse.put("followerCount", followerCount);
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 내 팔로잉(내가 등록한 사람) 목록을 가져오기 (완료)
	// 입력 데이터 : userId
	// 출력 데이터 : user_id,user_profile,user_nickname,user_intro... Dto정보 반환
	@GetMapping("/following/list")
	public List<FollowDto> findFollowingList(FollowDto followDto) {
		return followservice.findFollowingList(followDto);
	}
	
	// 내 팔로워(나를 등록한 사람) 목록을 가져오기 (완료)
	// 입력 데이터 : userId
	// 출력 데이터 : user_id,user_profile,user_nickname,user_intro... Dto정보 반환
	@GetMapping("/follower/list")
	public List<FollowDto> findFollowerList(FollowDto followDto) {
		return followservice.findFollowerList(followDto);
	}
	
	// 팔로우한 유저인지 확인 (완료)
	// 입력 데이터 : userId,followeeId
	// 출력 데이터 : message, followStatus
	@GetMapping("/check")
	public ResponseEntity<Map<String, Object>> checkFollowStatus(FollowDto followDto) {
		Integer followStatus = followservice.checkFollowStatus(followDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("message", (followStatus >= 1) ? "팔로우 중인 유저에요" : "팔로우가 아니에요");
		jsonResponse.put("followStatus", followStatus);
		return ResponseEntity.ok(jsonResponse);
	}
	
	// 팔로우 취소(내가 등록한 사람 삭제)
	// 입력 데이터 : userId,followeeId
	// 출력 데이터 : message
	@DeleteMapping("/unfollow")
	public ResponseEntity<Map<String, Object>> deletefollowUser(@RequestBody FollowDto followDto) {
		Integer unfollow = followservice.deletefollowUser(followDto);
		Map<String, Object> jsonResponse = new HashMap<>();
		jsonResponse.put("message", (unfollow >= 1) ? "팔로우를 해제했어요" : "팔로우 상태가 아니에요");
		jsonResponse.put("unfollow", unfollow);
		return ResponseEntity.ok(jsonResponse);
	}
	
}

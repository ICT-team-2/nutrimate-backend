package com.nutrimate.nutrimatebackend.model.follow;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("FollowDto")
public class FollowDto {
	private int followId; // 팔로우 번호
	private int followeeId; // 팔로잉
	private String createdDate; // 팔로우한 날짜
	private int followerId; // 팔로워
	private int isFollowing; // 팔로우 유무
	
	private int userId; // 유저아이디
	private String userProfile; // 프로필사진
	private String userNick; // 닉네임
	private String userIntro; // 자기소개
	
	private int recordId; // 기록번호
}

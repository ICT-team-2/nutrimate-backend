package com.nutrimate.nutrimatebackend.model.profile;


import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("MemberProfileDto")
public class MemberProfileDto {
	int userId;// 유저 pk
	String userNick;// 닉네임
	String userUid;// 아이디
	String userProfile; // 프로필 사진 경로
	String userIntro; // 자기소개
	
	int postCount; // 작성한 게시글 수
	int followerCount; // 팔로워 수
	int followingCount; // 팔로잉 수
	
	
}

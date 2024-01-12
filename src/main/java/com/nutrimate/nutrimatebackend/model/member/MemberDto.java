package com.nutrimate.nutrimatebackend.model.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDto {
	private String userName; // 이름
	private String userPwd; // 비밀번호
	private String userPhone; // 전화번호
	private String userProfile; // 프로필 사진 경로
	private String userRole; // 권한
	private String userJoindate; //가입일
	private String provider; // OAuth2 로그인(구글, 네이버, 카카오)
	private String providerId; // 해당 provider 서비스에서의 pk
}

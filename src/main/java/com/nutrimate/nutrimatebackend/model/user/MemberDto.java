package com.nutrimate.nutrimatebackend.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDto {
	String userName; // 이름
	String userPwd; // 비밀번호
	String userPhone; // 전화번호
	String userProfile; // 프로필 사진 경로
	String userRole; // 권한
	String userJoindate; //가입일
	String provider; // OAuth2 로그인(구글, 네이버, 카카오)
	String providerId; // 해당 provider 서비스에서의 pk
}

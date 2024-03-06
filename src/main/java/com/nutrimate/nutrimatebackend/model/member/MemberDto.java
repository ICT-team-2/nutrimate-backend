package com.nutrimate.nutrimatebackend.model.member;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Alias("MemberDto")
@ToString
@Builder
public class MemberDto {
	public static final String OAUTH_PASSWORD = "asdsafgwegFSDFSADFADS";
	
	private long userId;
	private String userName; // 이름
	private String userNick;
	private String userPhone = ""; // 전화번호
	private String userProfile; // 프로필 사진 경로
	private String userRole = "ROLE_USER"; // 권한
	private String createdDate; // 가입일
	private double userHeight;
	private double userWeight;
	private String userEmail = "";
	private String userGender = "";
	private String userSportHard = "MEDIUM";
	private String userAllergy = "";
	private double userCal;
	private String userDiet = "";
	private String deleted = "N";
	private String userIntro = "";
	
	private int carbo;
	private int protein;
	private int province;
	
	// 일반 로그인(CommonUser)
	private String userUid;
	private String userPwd;
	// OAuth2(OAuth2User)
	private String provider; // OAuth2 로그인(구글, 네이버, 카카오)
	private String providerId; // 해당 provider 서비스에서의 pk
	
	public List<String> getRoleList() {
		if (this.userRole.length() > 0) {
			return Arrays.asList(this.userRole.split(","));
		}
		return new ArrayList<>();
	}
	
	public List<String> getAllergyList() {
		if (this.userAllergy != null) {
			return Arrays.asList(this.userAllergy.split(","));
		} else {
			return new ArrayList<>();
		}
	}
}


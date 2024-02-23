package com.nutrimate.nutrimatebackend.model.member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
  private String userPhone; // 전화번호
  private String userProfile; // 프로필 사진 경로
  private String userRole; // 권한
  private String createdDate; // 가입일
  private long userHeight;
  private long userWeight;
  private String userEmail;
  private String userGender;
  private String userSportHard;
  private long userCal;
  private String userDiet;
  private String deleted;
  private String userIntro;
  private long carbo;
  private long protein;
  private long province;

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


}


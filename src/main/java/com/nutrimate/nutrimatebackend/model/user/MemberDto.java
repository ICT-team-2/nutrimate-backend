package com.nutrimate.nutrimatebackend.model.user;

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
  long userId;
  String userName; // 이름
  String userNick;
  String userPhone; // 전화번호
  String userProfile; // 프로필 사진 경로
  String userRole; // 권한
  String createdDate; // 가입일
  String userHeight;
  String userWeight;
  String userEmail;
  String userGender;
  String userSportHard;
  String userCal;
  String userDiet;
  String deleted;
  String userIntro;


  // 일반 로그인(CommonUser)
  String userUid;
  String userPwd;

  // OAuth2(OAuth2User)
  String provider; // OAuth2 로그인(구글, 네이버, 카카오)
  String providerId; // 해당 provider 서비스에서의 pk

  public List<String> getRoleList() {
    if (this.userRole.length() > 0) {
      return Arrays.asList(this.userRole.split(","));
    }
    return new ArrayList<>();
  }

}

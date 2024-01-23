package com.nutrimate.nutrimatebackend.model.member;

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
@ToString
@Builder
@Alias("FollowDto")
public class FollowDto {
  private String followId; // 팔로우 번호
  private String followeeId; // 팔로잉
  private String createdDate; // 팔로우한 날짜
  private String followerId; // 팔로워

  private String userId; // 유저아이디
  private String userProfile; // 프로필사진
  private String userNick; // 닉네임
  private String userIntro; // 자기소개
}

package com.nutrimate.nutrimatebackend.model.member;

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
public class DietSportRecordDto {
  private String userId; // 유저아이디
  private String userNick; // 닉네임

  private String foodName; // 음식이름
  private String foodCal; // 음식칼로리

  private String sportTime; // 운동시간
  private String sportName; // 운동이름
  private String sportCal; // 운동(소모)칼로리
}

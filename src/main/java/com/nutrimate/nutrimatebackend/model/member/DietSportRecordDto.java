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
  private int userId; // 유저아이디
  private int recordId; // 기록아이디
  private int dietrecordId; // 식단기록아이디
  private int exerciseId; // 운동기록아이디

  private int foodId; // 음식아이디
  private String foodName; // 음식이름
  private String foodCal; // 음식칼로리

  private int sportId; // 운동아이디
  private String sportName; // 운동이름
  private int sportMet; // Met
  private int sportTime; // 운동시간
  private int sportCal; // 운동(소모)칼로리
}

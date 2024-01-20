package com.nutrimate.nutrimatebackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
  String userUid;
  String userPwd;
}

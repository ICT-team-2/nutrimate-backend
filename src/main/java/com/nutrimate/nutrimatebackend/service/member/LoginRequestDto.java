package com.nutrimate.nutrimatebackend.service.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
	Long userId;
	String userUid;
	String userPwd;
}

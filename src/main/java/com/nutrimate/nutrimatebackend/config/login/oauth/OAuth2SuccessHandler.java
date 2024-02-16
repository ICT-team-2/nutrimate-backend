package com.nutrimate.nutrimatebackend.config.login.oauth;

import com.nutrimate.nutrimatebackend.config.login.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.util.JWTOkens;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
		log.info("successfulAuthentication 실행 : 인증 완료 뜻");
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		
		Map<String, Object> payloads = new HashMap<>();// 사용자 임의 데이타 추가
		long expirationTime = 1000 * 60 * 3;// 토큰의 만료시간 설정(30)
		int status = 1;
		String accessToken = JWTOkens.createToken(principalDetails.getMemberDto().getUserUid(),
				payloads, expirationTime, JWTOkens.ACCESS);
		Cookie accessCookie = new Cookie("ACCESS", accessToken);
		log.info(accessCookie);
		accessCookie.setPath("/");
		response.addCookie(accessCookie);
		
		if (status == JWTOkens.ACCESS) {
			expirationTime = 1000 * 60 * 60 * 3;// 토큰의 만료시간 설정(3시간)
			String refreshToken = JWTOkens.createToken(principalDetails.getMemberDto().getUserUid(),
					payloads, expirationTime, JWTOkens.REFRESH);
			Cookie refreshCookie = new Cookie("REFRESH", refreshToken);
			refreshCookie.setPath("/");
			log.info(refreshCookie);
			response.addCookie(refreshCookie);
		}
		response.getWriter().println("success");
	}
	
}

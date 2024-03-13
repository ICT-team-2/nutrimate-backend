package com.nutrimate.nutrimatebackend.config.login.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.config.login.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import com.nutrimate.nutrimatebackend.service.member.MemberService;
import com.nutrimate.nutrimatebackend.util.JWTOkens;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 시큐리티가 filter가지고 있는데 그 필터 중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 탄다
// 만약에 권한이 인증이 필요한 주소가 아니라면 이 필터를 안탄다
@Log4j2
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private MemberMapper memberMapper;
	private BCryptPasswordEncoder passwordEncoder;
	private ObjectMapper objectMapper;
	private String domain;
	
	public JwtAuthorizationFilter(
			AuthenticationManager authenticationManager,
			MemberMapper memberMapper, BCryptPasswordEncoder passwordEncoder,
			ObjectMapper objectMapper, String domain
			// ,MemberService memberService
	) {
		super(authenticationManager);
		this.memberMapper = memberMapper;
		this.passwordEncoder = passwordEncoder;
		// this.memberService = memberService;
		this.objectMapper = objectMapper;
		this.domain = domain;
	}
	
	
	// 인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게 됨.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	                                FilterChain chain) throws IOException, ServletException {
		// super.doFilterInternal(request, response, chain);
		log.info("인증이나 권한이 필요한 주소 요청이 됨");
		
		String jwtHeader = JWTOkens.getToken(request, "ACCESS");
		log.info("jwtHeader :" + jwtHeader);
		
		// // header가 있는지 확인
		if (jwtHeader == null || jwtHeader.isBlank()) {
			chain.doFilter(request, response);
			return;
		}
		
		// JWT 토큰을 검증해서 정상적인 사용자인지 확인
		String accessToken = request.getHeader("ACCESS");
		log.info("accessToken : " + accessToken);
		accessToken = JWTOkens.getToken(request, "ACCESS");
		String refreshToken = JWTOkens.getToken(request, "REFRESH");
		
		
		Map<String, Object> payload = null;
		try {
			payload = JWTOkens.getTokenPayloads(refreshToken, JWTOkens.REFRESH);
		} catch (Exception e) {
			JWTOkens.removeToken(request, response);
		}
		log.info("payload : " + payload);
		
		String userUid = payload.get("sub").toString();
		log.info("username : " + userUid);
		// 서명이 정상적으로 됨
		if (userUid != null) {
			MemberDto userEntity = memberMapper.findCommonMemberByUid(userUid);
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity, passwordEncoder);
			log.info("getAuthorities : " + principalDetails.getAuthorities());
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
					null, principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			MemberService memberService =
					(MemberService) request.getServletContext().getAttribute("service");
			
			// accessToken = JWTOkens.getToken(request, "ACCESS");
			// String accessToken =
			// JWTOkens.getToken(request, request.getServletContext().getInitParameter("ACCESS"));
			// String refreshToken =
			// JWTOkens.getToken(request, request.getServletContext().getInitParameter("REFRESH"));
			
			// System.out.println("accessToken1111111111 :" + accessToken);
			// System.out.println("refreshToken2222222222 :" + refreshToken);
			if (!accessToken.isEmpty()) {
				int accessVerifyStatus = JWTOkens.verifyToken(accessToken, JWTOkens.ACCESS);
				
				switch (accessVerifyStatus) {
					case JWTOkens.SIGNED:
						break;
					case JWTOkens.EXPIRED:
						int refreshVerifyStatus = JWTOkens.verifyToken(refreshToken, JWTOkens.REFRESH);
						switch (refreshVerifyStatus) {
							case JWTOkens.EXPIRED:
								// JWTOkens.createToken(refreshToken, null, refreshVerifyStatus,
								// accessVerifyStatus);
								log.info("ddd");
							case JWTOkens.SIGNED:
								// access 토큰 재발급 후 쿠키에 담기
								Map<String, Object> payloads = new HashMap<>();// 사용자 임의 데이타 추가
								long expirationTime = 1000 * 30;// 토큰의 만료시간 설정(30)
								int status = 1;
								accessToken =
										JWTOkens.createToken(userUid, payloads, expirationTime, JWTOkens.ACCESS);
								Cookie accessCookie = new Cookie("ACCESS", accessToken);
								log.info(accessCookie);
								accessCookie.setPath("/");
								if (domain != null) {
									accessCookie.setDomain(domain);
								}
								response.addCookie(accessCookie);
								// setUserInfo(refreshToken, request, response, memberService, userId);
								break;
							case JWTOkens.UNSIGNED:
								JWTOkens.removeToken(request, response);
								loginFail(response);
								return;
						}
						break;
					case JWTOkens.UNSIGNED: {
						JWTOkens.removeToken(request, response);
						loginFail(response);
						return;
					}
				}
			}
			
			
		}
		// MemberService memberService =
		// (MemberService) request.getServletContext().getAttribute("service");
		// accessToken =
		// JWTOkens.getToken(request, request.getServletContext().getInitParameter("ACCESS"));
		//
		//
		// String refreshToken =
		// JWTOkens.getToken(request, request.getServletContext().getInitParameter("REFRESH"));
		
		
		// if (!accessToken.isEmpty()) {
		// int accessVerifyStatus = JWTOkens.verifyToken(accessToken, JWTOkens.ACCESS);
		// switch (accessVerifyStatus) {
		// case JWTOkens.SIGNED:
		// case JWTOkens.EXPIRED:
		// int refreshVerifyStatus = JWTOkens.verifyToken(refreshToken, JWTOkens.REFRESH);
		// switch (refreshVerifyStatus) {
		// case JWTOkens.EXPIRED:
		// JWTOkens.getToken(request, refreshToken);
		// System.out.println("DDDD");
		// case JWTOkens.SIGNED:
		// setUserInfo(refreshToken, request, response, memberService, userId);
		// break;
		// case JWTOkens.UNSIGNED:
		// JWTOkens.removeToken(request, response);
		// loginFail(response);
		// }
		// break;
		// case JWTOkens.UNSIGNED: {
		// JWTOkens.removeToken(request, response);
		// loginFail(response);
		// }
		// }
		// }
		chain.doFilter(request, response);
	}
	
	public void loginFail(HttpServletResponse response) throws IOException {
		Map<String, String> result = new HashMap<>();
		result.put("message", "fail");
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
		response.getWriter().print(json);
	}
	
	// public void setUserInfo(String refreshToken, HttpServletRequest request,
	// HttpServletResponse response, MemberService memberService, Long userId) {
	// Map<String, Object> payload = JWTOkens.getTokenPayloads(refreshToken, JWTOkens.REFRESH);
	// request.setAttribute("userId", payload.get("user_id"));
	// MemberDto memberDto = memberService.findMemberInfoById(userId);
	// request.setAttribute("member", memberDto);
	// }
	
}

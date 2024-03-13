package com.nutrimate.nutrimatebackend.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@Component
public class JWTOkens {
	
	public static final int ACCESS = 1;
	public static final int REFRESH = 2;
	public static final int SIGNED = 200;
	public static final int UNSIGNED = 300;
	public static final int EXPIRED = 400;
	private static SecretKey accessKey;
	private static SecretKey refreshKey;
	private static String secret;
	
	@Value("${domain}")
	private static String domain;
	
	/**
	 * JWT토큰을 생성해서 반환하는 메소드
	 *
	 * @param username       사용자 아이디
	 * @param payloads       추가로 사용자 정보를 저장하기 위한 Claims
	 * @param expirationTime 토큰 만료 시간(15분에서 몇 시간이 적당).단위는 천분의 1초
	 *
	 * @return
	 */
	
	public static String createToken(String username, Map<String, Object> payloads,
	                                 long expirationTime, int status) {
		
		// JWT 토큰의 만료 시간 설정
		long currentTimeMillis = System.currentTimeMillis();// 토큰의 생성시간
		expirationTime = currentTimeMillis + expirationTime; // 토큰의 만료시간
		
		// Header 부분 설정
		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
		
		// * 아래 세줄 사용시 .setClaims(payloads)->.setClaims(claims로 변경) Claims claims=
		// * Jwts.claims().setSubject(username); claims.putAll(payloads); claims.put("roles", "권한");
		
		
		JwtBuilder builder = Jwts.builder().header().add(headers).and()// Headers 설정
				.claims(payloads)// Claims 설정(기타 페이로드)
				.subject(username)// 사용자 ID 설정
				.issuedAt(new Date())// 생성 시간을 설정
				.expiration(new Date(expirationTime))// 만료 시간 설정(필수로 설정하자.왜냐하면 토큰(문자열이라)은 세션처럼 제어가 안된다)
				.signWith(status == ACCESS ? accessKey : refreshKey, Jwts.SIG.HS256);// 비밀 키로 JWT를 서명
		
		// JWT 생성
		String jwt = builder.compact();
		return jwt;
	}
	
	/**
	 * 발급한 토큰의 payloads부분을 반환하는 메소드
	 *
	 * @param token 발급토큰
	 *
	 * @return 토큰의 payloads부분 반환
	 */
	
	
	public static Map<String, Object> getTokenPayloads(String token, int status) throws Exception {
		
		Map<String, Object> claims = new HashMap<>();
		
		try {
			// JWT토큰 파싱 및 검증
			claims = Jwts.parser().verifyWith(status == ACCESS ? accessKey : refreshKey).build()// 서명한
					// 비밀키로 검증
					.parseSignedClaims(token)// parseClaimsJws메소드는 만기일자 체크
					.getPayload();
			return claims;
		} catch (Exception e) {
			// 유효하지 않는 토큰
			log.info(e.getMessage(), e);
			throw new RuntimeException("유효하지 않은 토큰입니다");
		}
	}/////////////////////////////////
	
	/**
	 * 유효한 토큰인지 검증하는 메소드
	 *
	 * @param token 발급토큰
	 *
	 * @return 유효한 토큰이면 true,만료가됬거나 변조된 토큰인 경우 false반환
	 */
	
	
	public static int verifyToken(String token, int status) {
		try {
			// JWT토큰 파싱 및 검증
			Jws<Claims> accessClaims =
					Jwts.parser().verifyWith(status == ACCESS ? accessKey : refreshKey).build()// 서명한 비밀키로 검증
							.parseSignedClaims(token);// parseClaimsJws메소드는 만기일자 체크
			// 토큰의 유효성과 만료일자 확인
			System.out.println("만기일자:" + accessClaims.getPayload().getExpiration());
			
		} catch (ExpiredJwtException e) {
			System.out.println("토큰 시간 만료");
			return EXPIRED;
			// e.printStackTrace();
			// System.out.println("유효하지 않은 토큰입니다:"+e.getMessage());
		} catch (SignatureException | MalformedJwtException e) {
			System.out.println("서명 검증 실패");
			return UNSIGNED;
		}
		return SIGNED;
	}/////////////////////////////////
	
	/**
	 * 문자열인 발급된 토큰을 요청헤더의 쿠키에서 읽어오는 메소드
	 *
	 * @param request    요청헤더에서 쿠키를 읽어오기 위한 HttpServletRequest객체
	 * @param cookieName 토큰 발급시 설정한 쿠키명
	 *
	 * @return 발급된 토큰
	 */
	
	
	public static String getToken(HttpServletRequest request, String cookieName) {
		// 발급한 토큰 가져오기
		Cookie[] cookies = request.getCookies();
		String token = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					token = cookie.getValue();
					int status = verifyToken(token, ACCESS);
					if (status == 400) {
						return token = "";
					}
					return token;
				}
			}
		}
		return token;
	}//
	
	/**
	 * 토큰을 삭제하는 메소드
	 *
	 * @param request  HttpServletRequest객체
	 * @param response HttpServletRequest객체
	 */
	
	
	public static void removeToken(HttpServletRequest request, HttpServletResponse response) {
		log.info("removeToken 실행");
		log.info("domain: " + domain);
		Cookie accessCookie = new Cookie("ACCESS", "");
		accessCookie.setPath("/");
		if (domain != null)
			accessCookie.setDomain(domain);
		accessCookie.setMaxAge(0);
		Cookie refreshCookie = new Cookie("REFRESH", "");
		refreshCookie.setPath("/");
		if (domain != null)
			refreshCookie.setDomain(domain);
		refreshCookie.setMaxAge(0);
		response.addCookie(accessCookie);
		response.addCookie(refreshCookie);
	}//
	// static {
	// byte[]
	// secret=Base64.getEncoder().encodeToString(secretkey.getBytes()).getBytes(StandardCharsets.UTF_8);
	// secretKey= Keys.hmacShaKeyFor(secret);
	// }
	
	@PostConstruct
	public void init() {
		byte[] secretTobytes =
				Base64.getEncoder().encodeToString(secret.getBytes()).getBytes(StandardCharsets.UTF_8);
		accessKey = Jwts.SIG.HS256.key().build();
		secretTobytes =
				Base64.getEncoder().encodeToString(secret.getBytes()).getBytes(StandardCharsets.UTF_8);
		refreshKey = Jwts.SIG.HS256.key().build();
	}
	
	@Value("{$access-key,refresh-key}")
	private void setSecret(String secret) {
		this.secret = secret;
	}
	@Value("${domain}")
	private void setDomain(String domain) {
		this.domain = domain;
	}
	
}

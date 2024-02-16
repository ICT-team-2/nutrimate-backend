package com.nutrimate.nutrimatebackend.config.login.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.config.login.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.dto.member.LoginRequestDto;
import com.nutrimate.nutrimatebackend.util.JWTOkens;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;
  // private ObjectMapper objectMapper = new ObjectMapper();

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager
  // ,ObjectMapper objectMapper
  ) {
    this.authenticationManager = authenticationManager;
    // this.objectMapper = objectMapper;
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    log.info("JwtAuthenticationFilter : 로그인 시도 중");

    ObjectMapper om = new ObjectMapper();
    LoginRequestDto loginRequestDto = null;


    try {
      loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
      log.info("loginRequestDto : " + loginRequestDto);
    } catch (IOException e) {
      log.error("로그인 시도 실패", e);
    }

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequestDto.getUserUid(),
            loginRequestDto.getUserPwd());
    log.info("authenticationToken : " + authenticationToken);

    Authentication authentication = null;

    try {
      authentication = authenticationManager.authenticate(authenticationToken);
      log.info("authentication: " + authentication);
    } catch (AuthenticationException e) {
      log.error("Authentication failure", e);
    }


    // => 로그인이 되었다는 뜻
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    log.info("로그인 완료 : " + principalDetails.getMemberDto().getUserUid());

    return authentication;
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    log.info("successfulAuthentication 실행 : 인증 완료 뜻");
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

    Map<String, Object> payloads = new HashMap<>();// 사용자 임의 데이타 추가
    payloads.put("userInfo", principalDetails.getMemberDto());
    long expirationTime = 1000 * 60 * 30;// 토큰의 만료시간 설정(30)
    int status = 1;
    String accessToken = JWTOkens.createToken(principalDetails.getMemberDto().getUserUid(),
        payloads, expirationTime, JWTOkens.ACCESS);
    Cookie accessCookie = new Cookie("ACCESS", accessToken);
    accessCookie.setPath("/");
    log.info(accessCookie);
    response.addCookie(accessCookie);

    if (status == JWTOkens.ACCESS) {
      expirationTime = 1000 * 60 * 60 * 3;// 토큰의 만료시간 설정(3시간)
      String refreshToken = JWTOkens.createToken(principalDetails.getMemberDto().getUserUid(),
          payloads, expirationTime, JWTOkens.REFRESH);
      Cookie refreshCookie = new Cookie("REFRESH", refreshToken);
      refreshCookie.setPath("/");
      refreshCookie.setHttpOnly(true);
      refreshCookie.setSecure(true);
      log.info(refreshCookie);
      response.addCookie(refreshCookie);
    }
  }
}

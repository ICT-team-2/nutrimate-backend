package com.nutrimate.nutrimatebackend.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.config.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.dto.LoginRequestDto;
import com.nutrimate.nutrimatebackend.util.JWTOkens;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    System.out.println("JwtAuthenticationFilter:로그인 시도 중");

    ObjectMapper om = new ObjectMapper();
    LoginRequestDto loginRequestDto = null;


    try {
      loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
      System.out.println("loginRequestDto:" + loginRequestDto);
    } catch (IOException e) {
      e.printStackTrace();
    }

    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginRequestDto.getUserUid(),
            loginRequestDto.getUserPwd());
    System.out.println("authenticationToken: " + authenticationToken);


    Authentication authentication = authenticationManager.authenticate(authenticationToken);

    // => 로그인이 되었다는 뜻
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
    System.out.println("로그인 완료:" + principalDetails.getMemberDto().getUserUid());

    return authentication;
  }


  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException {
    System.out.println("successfulAuthentication 실행 : 인증 완료 뜻");
    PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();


    Map<String, Object> payloads = new HashMap<>();// 사용자 임의 데이타 추가
    long expirationTime = 1000 * 60 * 60 * 3;// 토큰의 만료시간 설정(3시간)
    String token = JWTOkens.createToken(principalDetails.getMemberDto().getUserUid(), payloads,
        expirationTime);
    Cookie cookie = new Cookie("Authorization", token);
    response.addCookie(cookie);

  }
}

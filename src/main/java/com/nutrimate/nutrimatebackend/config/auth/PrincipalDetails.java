package com.nutrimate.nutrimatebackend.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import lombok.Data;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다.(Security ContextHolder)
// 오브젝트 => Authentication 타입객체
// Authentication 안에 User 정보가 있어야 됨
// User 오브젝트타입 => UserDetails 타입객체

// Security Session =>Authentication => UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {


  private MemberDto memberDto;
  private Map<String, Object> attributes;

  // 일반 로그인
  public PrincipalDetails(MemberDto memberDto) {
    this.memberDto = memberDto;
  }

  // OAuth 로그인
  public PrincipalDetails(MemberDto memberDto, Map<String, Object> attributes) {
    this.memberDto = memberDto;
    this.attributes = attributes;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    memberDto.getRoleList().forEach(r -> {
      authorities.add(() -> {
        return r;
      });
    });
    return authorities;
  }

  @Override
  public String getPassword() {
    return memberDto.getUserPwd();
  }

  @Override
  public String getUsername() {
    return memberDto.getUserUid();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    // 우리사이트 1년동안 회원이 로그인 안하면 휴먼계정
    // 현재시간 - 로그인시간 => 1년 초과 return false;
    return true;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

}

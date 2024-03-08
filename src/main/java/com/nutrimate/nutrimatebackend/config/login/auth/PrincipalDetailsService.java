package com.nutrimate.nutrimatebackend.config.login.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.model.member.MemberDto;
import com.nutrimate.nutrimatebackend.service.member.MemberService;
import lombok.extern.log4j.Log4j2;


// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으 UserDetailService타입으로 loC되어 있는 loadUserByUsername 함수 실행
@Service
@Log4j2
public class PrincipalDetailsService implements UserDetailsService {

  private MemberService memberService;
  private BCryptPasswordEncoder passwordEncoder;

  public PrincipalDetailsService(MemberService memberService,
      BCryptPasswordEncoder passwordEncoder) {
    this.memberService = memberService;
    this.passwordEncoder = passwordEncoder;
  }


  // 시큐리티 session(내부 Authentication(내부 UserDetails))
  @Override
  public UserDetails loadUserByUsername(String userUid) throws UsernameNotFoundException {
    log.info("userUid: " + userUid);
    MemberDto userEntity = memberService.findCommonMemberByUid(userUid);
    log.info("userEntity: " + userEntity);

    if (userEntity != null) {
      return new PrincipalDetails(userEntity, passwordEncoder);
    }
    return null;
  }
}

package com.nutrimate.nutrimatebackend.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import com.nutrimate.nutrimatebackend.service.MemberService;


// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으 UserDetailService타입으로 loC되어 있는 loadUserByUsername 함수 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

  private MemberService memberService;

  public PrincipalDetailsService(MemberService memberService) {
    this.memberService = memberService;
  }


  // 시큐리티 session(내부 Authentication(내부 UserDetails))
  @Override
  public UserDetails loadUserByUsername(String userUid) throws UsernameNotFoundException {
    MemberDto userEntity = memberService.findByUser(userUid);
    System.out.println("userEntity" + userEntity);
    if (userEntity != null) {
      return new PrincipalDetails(userEntity);
    }
    return null;
  }
}

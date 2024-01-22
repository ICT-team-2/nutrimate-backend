package com.nutrimate.nutrimatebackend.config.oauth;


import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.config.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.mapper.MemberMapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import com.nutrimate.nutrimatebackend.service.MemberService;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


  private PasswordEncoder passwordEncoder;
  private MemberMapper memberMapper;
  private MemberService memberService;


  public PrincipalOauth2UserService(MemberService memberService, MemberMapper memberMapper,
      PasswordEncoder passwordEncoder) {
    this.memberService = memberService;
    this.passwordEncoder = passwordEncoder;
    this.memberMapper = memberMapper;
  }

  // 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    System.out.println("getClientRegistration:" + userRequest.getClientRegistration());// registrarionId로
    // 어떤 OAuth로
    // 로그인을 했는지
    // 확인
    System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue());

    OAuth2User oauth2User = super.loadUser(userRequest);
    // 구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code를 리턴 (OAuth-Client라이브러리)->AccessToken요청
    // userRequest 정보 -> loadUser함수 호출-> 구글로부터 회원프로필 받아줌
    System.out.println("getAttributes:" + oauth2User.getAttributes());


    // 회원가입을 강제로 진행
    com.nutrimate.nutrimatebackend.config.oauth.provider.OAuth2UserInfo oAuth2UserInfo = null;
    if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
      System.out.println("구글 로그인 요청");
      oAuth2UserInfo = new com.nutrimate.nutrimatebackend.config.oauth.provider.GoogleUserInfo(
          oauth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
      System.out.println("페이스북 로그인 요청");
      oAuth2UserInfo = new com.nutrimate.nutrimatebackend.config.oauth.provider.FacebookUserInfo(
          oauth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
      System.out.println("네이버 로그인 요청");
      oAuth2UserInfo = new com.nutrimate.nutrimatebackend.config.oauth.provider.NaverUserInfo(
          (Map) oauth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
      System.out.println("카카오 로그인 요청");
      oAuth2UserInfo = new com.nutrimate.nutrimatebackend.config.oauth.provider.KakaoUserInfo(
          (Map) oauth2User.getAttributes().get("response"));
    } else {
      System.out.println("우리는 구글, 페이스북, 네이버만 지원합니다");
    }

    String provider = oAuth2UserInfo.getProvider();
    String providerId = oAuth2UserInfo.getProviderId();
    String email = oAuth2UserInfo.getEmail();
    String name = oAuth2UserInfo.getName();
    String role = "ROLE_USER";


    MemberDto userEntity = memberMapper.findOAuthMemberByProviderId(providerId);

    if (userEntity == null) {
      System.out.println("OAuth 로그인이 최초");
      userEntity = MemberDto.builder().userName(name).userEmail(email).userRole(role)
          .provider(provider).providerId(providerId).build();

      memberService.insertOAuthMember(userEntity);
    } else {
      System.out.println("로그인을 이미 한 적이있습니다. 당신은 자동 회원가입이 되어있습니다");
    }
    System.out.println("id :" + userEntity.getUserUid());
    System.out.println("getName :" + userEntity.getUserName());
    System.out.println("oauth2User : " + oauth2User.getAttributes());
    System.out.println("userEntity : " + userEntity);

    return new PrincipalDetails(userEntity, oauth2User.getAttributes());
  }
}
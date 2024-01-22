package com.nutrimate.nutrimatebackend.config.oauth;


import com.nutrimate.nutrimatebackend.config.auth.PrincipalDetails;
import com.nutrimate.nutrimatebackend.config.oauth.provider.FacebookUserInfo;
import com.nutrimate.nutrimatebackend.config.oauth.provider.GoogleUserInfo;
import com.nutrimate.nutrimatebackend.config.oauth.provider.KakaoUserInfo;
import com.nutrimate.nutrimatebackend.config.oauth.provider.NaverUserInfo;
import com.nutrimate.nutrimatebackend.config.oauth.provider.OAuth2UserInfo;
import com.nutrimate.nutrimatebackend.mapper.MemberMapper;
import com.nutrimate.nutrimatebackend.model.user.MemberDto;
import com.nutrimate.nutrimatebackend.service.MemberService;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Log4j2
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
		log.info(
				"getClientRegistration:" + userRequest.getClientRegistration());// registrarionId로
		// 어떤 OAuth로
		// 로그인을 했는지
		// 확인
		log.info("getAccessToken:" + userRequest.getAccessToken().getTokenValue());

		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code를 리턴 (OAuth-Client라이브러리)->AccessToken요청
		// userRequest 정보 -> loadUser함수 호출-> 구글로부터 회원프로필 받아줌
		log.info("getAttributes:" + oauth2User.getAttributes());

		// 회원가입을 강제로 진행
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			log.info("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(
					oauth2User.getAttributes());
		}
		else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			log.info("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(
					oauth2User.getAttributes());
		}
		else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			log.info("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo(
					(Map) oauth2User.getAttributes().get("response"));
		}
		else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
			log.info("카카오 로그인 요청");
			oAuth2UserInfo = new KakaoUserInfo(
					(Map) oauth2User.getAttributes().get("response"));
		}
		else {
			log.info("우리는 구글, 페이스북, 네이버만 지원합니다");
		}

		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String email = oAuth2UserInfo.getEmail();
		String name = oAuth2UserInfo.getName();
		String role = "ROLE_USER";

		MemberDto userEntity = memberMapper.findOAuthMemberByProviderId(providerId);
		log.info("userEntity : " + userEntity);
		log.info("OAuth2UserInfo : " + oAuth2UserInfo.getProviderId());

		if (userEntity == null) {
			log.info("OAuth 로그인이 최초");
			userEntity = MemberDto.builder().userName(name).userEmail(email).userRole(role)
			                      .provider(provider).providerId(providerId).build();

			memberService.insertOAuthMember(userEntity);
		}
		else {
			log.info("로그인을 이미 한 적이있습니다. 당신은 자동 회원가입이 되어있습니다");
		}
		log.info("id :" + userEntity.getUserUid());
		log.info("getName :" + userEntity.getUserName());
		log.info("oauth2User : " + oauth2User.getAttributes());
		log.info("userEntity : " + userEntity);

		PrincipalDetails principalDetails
				= new PrincipalDetails(userEntity,
				                       oauth2User.getAttributes());
		log.info("principalDetails: " + principalDetails);
		log.info("principalDetails.getName(): " + principalDetails.getName());
		log.info("principalDetails.getPassword(): " + principalDetails.getPassword());
		log.info("principalDetails.getAuthorities(): " + principalDetails.getAuthorities());
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				principalDetails,
				null, principalDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}
}

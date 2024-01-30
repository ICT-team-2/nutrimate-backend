package com.nutrimate.nutrimatebackend.config.login.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributes; // oauth2User.getAttribute()
	
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}
	
	@Override
	public String getProvider() {
		return "kakao";
	}
	
	
}

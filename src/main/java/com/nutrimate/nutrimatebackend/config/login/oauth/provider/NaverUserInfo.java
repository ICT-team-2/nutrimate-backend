package com.nutrimate.nutrimatebackend.config.login.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributes; // oauth2User.getAttribute()
	
	
	// {id = ,email = ,name = }
	public NaverUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}
	
	@Override
	public String getProvider() {
		return "naver";
	}
	
	
}

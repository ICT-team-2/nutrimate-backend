package com.nutrimate.nutrimatebackend.config.login.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
	
	private Map<String, Object> attributes; // oauth2User.getAttribute()
	
	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String getProviderId() {
		return (String) attributes.get("sub");
	}
	
	@Override
	public String getProvider() {
		return "google";
	}
	
	
}

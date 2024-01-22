package com.nutrimate.nutrimatebackend.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Log4j2
public class LoggingOAuth2LoginAuthenticationFilter extends OAuth2LoginAuthenticationFilter {
	public LoggingOAuth2LoginAuthenticationFilter(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientService authorizedClientService) {
		super(clientRegistrationRepository, authorizedClientService);
		// default filter processes url is "/login/oauth2/code/*"
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(
				"/login/oauth2/code/*", "GET"));
	}

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request,
			HttpServletResponse response) {
		// Print log
		String code = request.getParameter(OAuth2ParameterNames.CODE);
		log.info("Received authorization code: " + code);

		// Proceed with the original attemptAuthentication method
		return super.attemptAuthentication(request, response);
	}
}

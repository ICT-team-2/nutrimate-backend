package com.nutrimate.nutrimatebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement((sessionManagement) ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				// 실행 에러 해결을 위해 authorizeHttpRequests -> authorizeRequests 임의로 변경
				.authorizeRequests((authorizeRequests) ->
						authorizeRequests
						        // 실행 에러 해결을 위해 requestMatchers -> antMatchers 임의로 변경
								.antMatchers("/user/**").authenticated()
								.antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
								.antMatchers("/admin/**").hasAnyRole("ADMIN")
								.anyRequest().permitAll()
				);
		return http.build();
	}
}

package com.nutrimate.nutrimatebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

//todo: 아래 내용 수정하여 사용할 것!!
@Configuration
public class SecurityConfig {
	
	
	@Bean

	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement((sessionManagement) -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// 실행 에러 해결을 위해 requestMatchers -> antMatchers로 바꿨습니다 (학원 pc에서만 오류남..)
				.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
//                .antMatchers("/user/**")
//            .authenticated().antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
//            .antMatchers("/admin/**").hasAnyRole("ADMIN")
						.anyRequest().permitAll());

		return http.build();
	}
}
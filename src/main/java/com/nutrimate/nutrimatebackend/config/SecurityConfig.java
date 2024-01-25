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
				.authorizeHttpRequests((authorizeRequests) ->
						authorizeRequests
								.antMatchers("/user/**").authenticated()
								.antMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
								.antMatchers("/admin/**").hasAnyRole("ADMIN")
								.antMatchers("/report/**").hasAnyRole("ADMIN")
								.anyRequest().permitAll()
				);
		return http.build();
	}
}

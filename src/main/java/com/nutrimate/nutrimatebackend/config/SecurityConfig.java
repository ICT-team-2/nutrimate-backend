package com.nutrimate.nutrimatebackend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.filter.CorsFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutrimate.nutrimatebackend.config.login.jwt.JwtAuthenticationFilter;
import com.nutrimate.nutrimatebackend.config.login.jwt.JwtAuthorizationFilter;
import com.nutrimate.nutrimatebackend.config.login.oauth.OAuth2SuccessHandler;
import com.nutrimate.nutrimatebackend.config.login.oauth.PrincipalOauth2UserService;
import com.nutrimate.nutrimatebackend.mapper.member.MemberMapper;
import com.nutrimate.nutrimatebackend.service.MemberService;
import lombok.extern.log4j.Log4j2;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Log4j2
public class SecurityConfig {

  @Autowired
  ClientRegistrationRepository clientRegistrationRepository;
  @Autowired
  OAuth2AuthorizedClientService authorizedClientService;
  @Autowired
  ObjectMapper objectMapper;


  private PrincipalOauth2UserService principalOauth2UserService;
  private CorsFilter corsFilter;
  private MemberMapper memberMapper;
  private MemberService memberService;


  public SecurityConfig(CorsFilter corsFilter,
      PrincipalOauth2UserService principalOauth2UserService, MemberMapper memberMapper) {
    this.corsFilter = corsFilter;
    this.principalOauth2UserService = principalOauth2UserService;
    this.memberMapper = memberMapper;
    this.memberService = memberService;
  }

  @Bean
  static BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder)
      throws Exception {


    http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
    http.csrf(t -> t.disable())
        .sessionManagement((sessionManagement) -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilter(corsFilter) // @CrossOrigin(인증 x), 시큐리티 필터에 등록인증(o)
        .addFilter(new JwtAuthenticationFilter(authenticationManager, objectMapper)) // AuthenticationManager
        .addFilter(new JwtAuthorizationFilter(authenticationManager, memberMapper, passwordEncoder,
            objectMapper)) // AuthenticationManager
        .authorizeHttpRequests(t -> t.requestMatchers("/api/v1/user/**").authenticated()
            .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
            .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN").anyRequest().permitAll())
        .formLogin(t -> t
            // .loginPage("/loginForm")
            .loginProcessingUrl("/login").defaultSuccessUrl("/"))
        .oauth2Login(t -> t
            // .loginPage("/loginForm")
            .userInfoEndpoint(endpoint -> endpoint.userService(principalOauth2UserService))
            .defaultSuccessUrl("http://localhost:5555/")
            .failureHandler((request, response, exception) -> {
              log.info("exception: ", exception);
              response.getWriter().println(exception.getMessage());
            }).successHandler((request, response, authentication) -> {
              log.info("authentication: ", authentication);
              response.getWriter().println("success");
            }).successHandler(new OAuth2SuccessHandler()));

    return http.build();
  }

}

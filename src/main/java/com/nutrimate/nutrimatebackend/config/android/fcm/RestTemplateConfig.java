package com.nutrimate.nutrimatebackend.config.android.fcm;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    RestTemplate restTemplate() {
        // 1. RestTemplateBuilder 사용
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        // 2. HttpClient 커스터마이징
        restTemplateBuilder.setConnectTimeout(Duration.ofMillis(3000)); // 요청 타임아웃 시간 설정

        // 3. RestTemplate 객체 생성 및 반환
        return restTemplateBuilder.build();
    }

}

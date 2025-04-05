package org.mycrawling.webcrawling.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    
    // CORS 설정
    public CorsConfigurationSource corsConfigurationSource() {
        // cors 설정 생성
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // 허용 출처
        config.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE")); // 허용 메소드
        config.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 요청 허용 (받는거)

        // 내가 작성한 Header 값이 노출되어야 프론트에서 사용할 수 있다
//        config.addExposedHeader("access"); // access header 값 노출 허용
//        config.addExposedHeader("username");
//        config.setAllowCredentials(true); // 쿠키/인증 정보 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 모든 경로에 요청
        return source;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // csrf 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable); // basic 로그인 비활성화
        http.formLogin(AbstractHttpConfigurer::disable); // 폼 로그인 비활성화

        // cors 설정 등록
        http.
                cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                );
        
        // 인증 경로 설정
        http.
                authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/crawl"
                        ).permitAll()
                );

        return http.build();
    }


}
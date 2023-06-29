package com.example.spring.Pro_two.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity   // 모든요청 url이 springsecurity 의 제어를 받도록하는 애너테이션
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // requestMatchers 요청에대한 규칙 지정 패턴 /admin/** admin으로 시작되는 url패턴에 대한 규칙을 지정하거나
                                                                                    // 특정 url에대한 규칙을 지정
                http.authorizeHttpRequests().requestMatchers("/**").permitAll();
        return http.build();
    }

    @Bean  //예외처리
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

package com.example.spring.Pro_two.config;


import com.example.spring.Pro_two.handler.FailHandler;
import com.example.spring.Pro_two.handler.SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
//@EnableWebSecurity   // 모든요청 url이 springsecurity 의 제어를 받도록하는 애너테이션
public class SecurityConfig  {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        SuccessHandler successHandler = new SuccessHandler();
        FailHandler failHandler = new FailHandler();

                                            // 여기서 지정한 url은  전부허용
        http.authorizeHttpRequests().requestMatchers("/joinForm","/login").permitAll()
                .anyRequest().authenticated()            //나머지 모든 url에 인증을 요구
                .and().formLogin().loginPage("/로그인페이지").loginProcessingUrl("/로그인이 실행되는 url").permitAll()
                .successHandler(successHandler).failureHandler(failHandler);
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

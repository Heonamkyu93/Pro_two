package com.example.spring.Pro_two.config;

import com.example.spring.Pro_two.repository.LoginRepository;
import com.example.spring.Pro_two.repository.LoginRepositoryDb;
import com.example.spring.Pro_two.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class LoginConfig {
    private final JdbcTemplate jdbcTemplate;

    public LoginConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public LoginService loginService(){
        return new LoginService(loginRepository());
    }
    @Bean
    public LoginRepository loginRepository(){
        return new LoginRepositoryDb(jdbcTemplate);
    }
}

package com.example.spring.Pro_two.config;

import com.example.spring.Pro_two.repository.MemberRepository;
import com.example.spring.Pro_two.repository.MemberRepositoryDb;
import com.example.spring.Pro_two.service.FileProcess;
import com.example.spring.Pro_two.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class HomeConfig {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;




    public HomeConfig(DataSource dataSource, JdbcTemplate jdbcTemplate,PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder=passwordEncoder;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository(), fileProcess() ,passwordEncoder);
    }
    @Bean
    public MemberRepository memberRepository(){
        return new MemberRepositoryDb(jdbcTemplate);
    }
    @Bean
    public FileProcess fileProcess(){
        return new FileProcess();
    }

}

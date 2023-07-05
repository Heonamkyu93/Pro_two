package com.example.spring.Pro_two.config;

import com.example.spring.Pro_two.repository.JoinRepository;
import com.example.spring.Pro_two.repository.JoinRepositoryDb;
import com.example.spring.Pro_two.service.FileProcess;
import com.example.spring.Pro_two.service.JoinService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class JoinConfig {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public JoinConfig(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    @Bean
    public JoinService memberService() {
        return new JoinService(memberRepository(), fileProcess(), passwordEncoder, mailSender, templateEngine);
    }

    @Bean
    public JoinRepository memberRepository() {
        return new JoinRepositoryDb(jdbcTemplate);
    }

    @Bean
    public FileProcess fileProcess() {
        return new FileProcess();
    }
}

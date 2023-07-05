package com.example.spring.Pro_two.repository;

import com.example.spring.Pro_two.domain.LoginDto;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginRepositoryDb implements LoginRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRepositoryDb(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public LoginDto login(LoginDto loginDto) {
        return jdbcTemplate.query("SELECT * FROM TWMEMBER WHERE TWID=? and twpwd=?","아이디","비밀번호",);
        return null;
    }
}

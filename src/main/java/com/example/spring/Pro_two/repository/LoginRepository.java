package com.example.spring.Pro_two.repository;

import com.example.spring.Pro_two.domain.LoginDto;

public interface LoginRepository {
    LoginDto login(LoginDto loginDto);
}

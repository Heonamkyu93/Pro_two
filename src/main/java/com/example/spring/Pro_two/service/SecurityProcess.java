package com.example.spring.Pro_two.service;


import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityProcess {
    private final PasswordEncoder passwordEncoder;

    public SecurityProcess(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void crytoPassword(String twPwd) {
        passwordEncoder.encode(twPwd);
    }
}

package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class MemberService {

    private final MemberRepository memberRepository;
    private final FileProcess fileProcess;
    private  final  SecurityProcess securityProcess;
    private  final PasswordEncoder passwordEncoder;
    @Autowired
    public MemberService(MemberRepository memberRepository, FileProcess fileProcess, SecurityProcess securityProcess, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.fileProcess = fileProcess;
        this.securityProcess= securityProcess;
        this.passwordEncoder = passwordEncoder;
    }



    public void joinSave(MemberDto memberDto, ArrayList <MultipartFile> am) {
        fileProcess.fileSave(am,memberDto);
        memberDto.setTwPwd(passwordEncoder.encode(memberDto.getTwPwd()));
        System.out.println("ddddd=======================");

        //  securityProcess.crytoPassword(memberDto.getTwPwd());
        memberRepository.joinSave(memberDto,am);
    }
}

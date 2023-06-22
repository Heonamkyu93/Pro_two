package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public class MemberService {

    private final MemberRepository memberRepository;
    private final FileProcess fileProcess;

    @Autowired
    public MemberService(MemberRepository memberRepository, FileProcess fileProcess) {
        this.memberRepository = memberRepository;
        this.fileProcess = fileProcess;
    }



    public void joinSave(MemberDto memberDto, ArrayList <MultipartFile> am) {
        FileProcess fileProcess =new FileProcess();
        fileProcess.fileSave(am,memberDto);
        memberRepository.joinSave(memberDto,am);
    }
}

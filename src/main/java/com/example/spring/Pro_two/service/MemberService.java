package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import com.example.spring.Pro_two.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;
    private final FileProcess fileProcess;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, FileProcess fileProcess, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.fileProcess = fileProcess;
        this.passwordEncoder = passwordEncoder;
    }


    public void joinSave(MemberDto memberDto, ArrayList<MultipartFile> am, String twPicCat) {
        memberDto.setTwPwd(passwordEncoder.encode(memberDto.getTwPwd()));
        memberRepository.joinSave(memberDto, am);

        ArrayList<PicDto> picDtoArrayList = fileProcess.fileSave(am, twPicCat);
        for (PicDto picDto : picDtoArrayList) {
            Optional.ofNullable(picDto.getTwPicOri()).flatMap(TwPicOri -> Optional.ofNullable(picDto.getTwPicSer())).map(TwPicSer ->
             Optional.ofNullable(memberRepository.joinSavePic(picDto)));
        }

    }
}

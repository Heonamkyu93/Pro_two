package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import com.example.spring.Pro_two.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

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


    public String joinSave(MemberDto memberDto, ArrayList<MultipartFile> am, String twPicCat) {
        memberDto.setTwPwd(passwordEncoder.encode(memberDto.getTwPwd()));
        int re=memberRepository.joinSave(memberDto, am);
        if(re==-1){
            //   -1이면 멤버등록실패
        return ""; //멤버등록실패시 여기서 String리턴해서 컨트롤러 리턴값을 대체해서 다른페이지로 보내기
        } else {
       /* ArrayList<PicDto> picDtoArrayList = fileProcess.fileSave(am, twPicCat);
        for (PicDto picDto : picDtoArrayList) {
            Optional.ofNullable(picDto.getTwPicOri()).flatMap(TwPicOri -> Optional.ofNullable(picDto.getTwPicSer())).map(TwPicSer ->
                    {
                        return memberRepository.joinSavePic(picDto);
                    }
            );
        }
       */
            Stream<PicDto> picDtoStream = fileProcess.fileSave(am, twPicCat,re).stream();
            picDtoStream.forEach(dto -> {
                Optional<String> oriname = Optional.ofNullable(dto.getTwPicOri());
                Optional<String> sername = Optional.ofNullable(dto.getTwPicSer());
                oriname.ifPresent(ori -> sername.ifPresent(ser -> memberRepository.joinSavePic(dto)));
            });

        }
        return "";
    }
}

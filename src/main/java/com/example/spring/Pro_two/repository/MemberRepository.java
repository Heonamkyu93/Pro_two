package com.example.spring.Pro_two.repository;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface MemberRepository {
    void joinSave(MemberDto memberDto, ArrayList<MultipartFile> am);

    int joinSavePic(PicDto picDto);
}

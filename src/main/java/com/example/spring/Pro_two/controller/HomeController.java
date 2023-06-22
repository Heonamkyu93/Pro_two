package com.example.spring.Pro_two.controller;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class HomeController {
    private final MemberService memberService;

    @Autowired
    HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/joinForm")
    public String joinForm() {

        return "Form/joinForm";
    }

    @PostMapping("/joinSave") //여기는 사진 한개만 받고 modelAttribute로 묶자 dto multi 필드 추가 메소드도
    public String joinSave(@Valid @ModelAttribute MemberDto memberDto
        , @RequestPart ArrayList<MultipartFile> am) {
        memberService.joinSave(memberDto,am);
        return null;
    }


}

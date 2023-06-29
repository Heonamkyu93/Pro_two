package com.example.spring.Pro_two.controller;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/joinSave")
    public String joinSave(/*@Valid */@ModelAttribute MemberDto memberDto
        , @RequestPart ArrayList<MultipartFile> am,
                                      @RequestParam("twPicCat")String twPicCat) {
        memberService.joinSave(memberDto,am,twPicCat);
        return null;
    }


}

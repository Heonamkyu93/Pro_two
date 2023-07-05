package com.example.spring.Pro_two.controller;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.service.JoinService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class JoinController {
    private final JoinService joinService;

    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }
    @GetMapping("/joinForm")
    public String joinForm() {

        return "Form/joinForm";
    }

    @PostMapping("/joinSave")
    public String joinSave(/*@Valid */@ModelAttribute MemberDto memberDto
            , @RequestPart ArrayList<MultipartFile> am,
                                      @RequestParam("twPicCat") String twPicCat) throws MessagingException {
        String path= joinService.joinSave(memberDto, am, twPicCat);
        return path;
    }
}

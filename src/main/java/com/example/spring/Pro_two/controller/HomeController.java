package com.example.spring.Pro_two.controller;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index() throws MessagingException {
        return "index";
    }




}

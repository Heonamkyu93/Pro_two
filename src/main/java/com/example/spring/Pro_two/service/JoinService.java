package com.example.spring.Pro_two.service;

import com.example.spring.Pro_two.domain.MemberDto;
import com.example.spring.Pro_two.domain.PicDto;
import com.example.spring.Pro_two.repository.JoinRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class JoinService {

    private final JoinRepository joinRepository;
    private final FileProcess fileProcess;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Autowired
    public JoinService(JoinRepository joinRepository, FileProcess fileProcess, PasswordEncoder passwordEncoder, JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.joinRepository = joinRepository;
        this.fileProcess = fileProcess;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    public String joinSave(MemberDto memberDto, ArrayList<MultipartFile> am, String twPicCat) throws MessagingException {
        memberDto.setTwPwd(passwordEncoder.encode(memberDto.getTwPwd()));
        MailSend mailSend = new MailSend(mailSender,templateEngine);
        String code = createCode();
        memberDto.setCode(code);
        int re= joinRepository.joinSave(memberDto, am);
        if(re==-1){
            //   -1이면 멤버등록실패
        return "Form/joinForm"; //멤버등록실패시 여기서 String리턴해서 컨트롤러 리턴값을 대체해서 다른페이지로 보내기
        } else {
            Stream<PicDto> picDtoStream = fileProcess.fileSave(am, twPicCat,re).stream();
            picDtoStream.forEach(dto -> {
                Optional<String> oriname = Optional.ofNullable(dto.getTwPicOri());
                Optional<String> sername = Optional.ofNullable(dto.getTwPicSer());
                oriname.ifPresent(ori -> sername.ifPresent(ser -> joinRepository.joinSavePic(dto)));
            });
            mailSend.sendHtml(memberDto.getTwEmail(),"회원가입 인증메일",code);
            return "redirect:/index";
        }
    }
    private String createCode() {
        int random = (int) (Math.random() * 1000000);
        String code = String.valueOf(random);
        return code;
    }
}

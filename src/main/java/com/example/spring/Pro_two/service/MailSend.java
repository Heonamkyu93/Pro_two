package com.example.spring.Pro_two.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
@Component
public class MailSend {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @Autowired
    public MailSend(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }


    public void sendEmail(String to, String subject, String text) {  //일반텍스트 메일보낼때
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendHtml(String to, String subject,String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
                                        //일반 텍스트 메일 + html 메일
        helper.setTo(to);               //수신인
        helper.setSubject(subject);     // 제목
        helper.setText(setContext(code), true);    //false 면 html이 아닌 일반 텍스트로 처리
        mailSender.send(message);
    }
    private String setContext(String code) { // 타임리프 설정하는 코드
        Context context = new Context();
        context.setVariable("code", code); // Template에 전달할 데이터 설정
        return templateEngine.process("mail/mail", context); // mail.html
    }
    /*private String createCode() {
        int random = (int) (Math.random() * 1000000);
        String code = String.valueOf(random);
        return code;
    }*/
}

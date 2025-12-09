package com.jsp.ecommerce.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jsp.ecommerce.dto.UserDto;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine engine;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(UserDto userDto, int otp) {

        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("name", userDto.getName());

        String text = engine.process("email-template.html", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(userDto.getEmail());
            helper.setSubject("OTP Verification");
            helper.setText(text, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email");
        }
    }
}

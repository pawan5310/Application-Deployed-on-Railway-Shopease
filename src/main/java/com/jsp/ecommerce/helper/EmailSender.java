package com.jsp.ecommerce.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.jsp.ecommerce.dto.UserDto;
import com.resend.Resend;
import com.resend.services.emails.model.SendEmailRequest;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine engine;

    @Value("${spring.mail.username}")
    private String from;

    @Async   // IMPORTANT
    public void sendEmail(UserDto userDto, int otp) {

        try {
            Context context = new Context();
            context.setVariable("name", userDto.getName());
            context.setVariable("otp", otp);

            String html = engine.process("email-template", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("onboarding@resend.dev");
            helper.setTo(userDto.getEmail());
            helper.setSubject("OTP Verification");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace(); // LOG ONLY
        }
    }
}

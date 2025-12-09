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

    private final Resend resend;

    private final TemplateEngine engine;

    public EmailSender(
            TemplateEngine engine,
            @Value("${RESEND_API_KEY}") String apiKey
    ) {
        this.engine = engine;
        this.resend = new Resend(apiKey);
    }

    public void sendEmail(UserDto userDto, int otp) {

        try {
            // 1. Build Thymeleaf context
            Context context = new Context();
            context.setVariable("name", userDto.getName());
            context.setVariable("otp", otp);

            // 2. Render HTML (YOUR EXISTING TEMPLATE)
            String html = engine.process("email-template", context);

            // 3. Send email via Resend
            SendEmailRequest request = SendEmailRequest.builder()
                    .from("ShopEase <onboarding@resend.dev>")
                    .to(userDto.getEmail())
                    .subject("OTP Verification")
                    .html(html)
                    .build();

            resend.emails().send(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send OTP email");
        }
    }
}

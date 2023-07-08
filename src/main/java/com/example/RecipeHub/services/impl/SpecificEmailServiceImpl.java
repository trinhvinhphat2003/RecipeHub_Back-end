package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.dtos.request.RegisterRequest;
import com.example.RecipeHub.entities.MailInfo;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.EmailService;
import com.example.RecipeHub.services.SpecificEmailService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpecificEmailServiceImpl implements SpecificEmailService {

    private final EmailService emailService;

    public SpecificEmailServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void sendNewPasswordEmailInform(ForgottenPasswordDto request) throws Exception{
        final String subject = "New password for you RecipeHub account";
        final String htmlTemplate = "forgot-password-email";
        final Map<String, Object> properties = new HashMap<>();
        properties.put("name", request.getName());
        properties.put("password", request.getPassword());
        MailInfo mailInfo = MailInfo.builder()
                .receiver(request.getEmail())
                .subject(subject)
                .htmlTemplateName(htmlTemplate)
                .properties(properties)
                .build();
        emailService.sendEmailUsingHTMLTemplate(mailInfo);
    }

    @Override
    public void sendPasswordChangeReminder(User user) throws Exception {
        final String subject = "Your RecipeHub account's password has been changed";
        final String htmlTemplate = "inform-password-change";
        final Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getFullName());
        MailInfo mailInfo = MailInfo.builder()
                .receiver(user.getEmail())
                .subject(subject)
                .htmlTemplateName(htmlTemplate)
                .properties(properties)
                .build();
        emailService.sendEmailUsingHTMLTemplate(mailInfo);
    }

    @Override
    public void sendRegisterVerificationEmail(RegisterRequest registerRequest, String applicationPath, String token) throws Exception {
        final String subject = "Verify your email address";
        final String htmlTemplate = "verification-email";
        String verificationPath = applicationPath + "/api/v1/auth/verify-user?token=" + token;
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", registerRequest.getFullName());
        properties.put("verificationUrl", verificationPath);
        MailInfo mailInfo = MailInfo.builder()
                .receiver(registerRequest.getEmail())
                .subject(subject)
                .htmlTemplateName(htmlTemplate)
                .properties(properties)
                .build();
        // MailInfo mailInfo = new MailInfo(registerRequest.getEmail(), subject, VERIFICATION_EMAIL_HTML_TEMPLATE, properties);
        emailService.sendEmailUsingHTMLTemplate(mailInfo);
    }
}

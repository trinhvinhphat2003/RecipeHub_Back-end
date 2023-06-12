package com.example.RecipeHub.services.impl;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.RecipeHub.entities.MailInfo;
import com.example.RecipeHub.services.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String APPLICATION_URL = "";
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    
    

    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
		super();
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}

	@Override
    public void sendEmailUsingHTMLTemplate(MailInfo mailInfo) throws Exception{
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mailMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        Context context = new Context();
        context.setVariables(mailInfo.getProperties());

        String template = templateEngine.process(mailInfo.getHtmlTemplateName(), context);

        helper.setTo(mailInfo.getReceiver());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(template, true);

        javaMailSender.send(mailMessage);
    }
}
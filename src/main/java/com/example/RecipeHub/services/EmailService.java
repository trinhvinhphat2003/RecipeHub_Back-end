package com.example.RecipeHub.services;

import com.example.RecipeHub.entities.MailInfo;

public interface EmailService {
    void sendEmailUsingHTMLTemplate(MailInfo mailInfo) throws Exception;
}
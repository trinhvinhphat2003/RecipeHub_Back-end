package com.example.RecipeHub.eventListeners.listenners;

import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.services.SpecificEmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ForgotPasswordListener implements ApplicationListener<ForgotPasswordEvent> {
    private final SpecificEmailService emailService;

    public ForgotPasswordListener(SpecificEmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(ForgotPasswordEvent event) {
        try {
            emailService.sendNewPasswordEmailInform(event.getUserPasswordChangeDto());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
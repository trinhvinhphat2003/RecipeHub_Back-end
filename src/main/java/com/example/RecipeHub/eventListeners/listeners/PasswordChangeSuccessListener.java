package com.example.RecipeHub.eventListeners.listeners;

import com.example.RecipeHub.eventListeners.events.PasswordChangeSuccessEvent;
import com.example.RecipeHub.services.SpecificEmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PasswordChangeSuccessListener implements ApplicationListener<PasswordChangeSuccessEvent> {
    private final SpecificEmailService emailService;

    public PasswordChangeSuccessListener(SpecificEmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(PasswordChangeSuccessEvent event) {
        try {
            emailService.sendPasswordChangeReminder(event.getUser());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
